package com.dev.ops.organisation.web.interceptors;

import java.math.BigDecimal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.dev.ops.common.constants.CommonConstants;
import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.device.authentication.service.constants.DeviceAuthenticationConstants;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.organisation.web.constants.WebConstants;

@Component
public class SessionValidationInterceptor implements HandlerInterceptor {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Value(value = "${authentication.service.url}")
	private String authenticationServiceURL;

	@Autowired
	@Value(value = "${employee.service.url}")
	private String employeeServiceURL;

	private final int sessionDuration;

	protected static final String SSO_TOKEN = "ssoToken";

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(SessionValidationInterceptor.class);

	@Autowired
	public SessionValidationInterceptor(@Value("${sso.token.valid.duration.hours}") final String sessionDurationHours) {
		this.sessionDuration = Integer.valueOf(sessionDurationHours) * 60 * 60;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object object, final Exception exception) throws Exception {

	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object) throws Exception {
		boolean shouldProceedWithRequest = false;

		//Do not check valid session for login and logout URLs.
		if(StringUtils.endsWith(request.getRequestURL(), "logout")) {
			shouldProceedWithRequest = true;
		} else {

			//Create new dummy session if the cookie is not set or empty ssoToken
			//Else if ssoToken is present then check whether it is valid. If invalid redirect to logout page.
			//TODO: After implementation of login page in organisation, remove this session creation logic from and add it to login success service.
			Cookie ssoTokenCookie = this.getCookie(SSO_TOKEN, request);
			if(ssoTokenCookie == null || StringUtils.isEmpty(ssoTokenCookie.getValue())) {
				ssoTokenCookie = this.createCookie(SSO_TOKEN, request);
				response.addCookie(ssoTokenCookie);
				shouldProceedWithRequest = true;
			} else {
				shouldProceedWithRequest = this.isSSOTokenValid(ssoTokenCookie.getValue());
				//TODO:remove this cookie if it is invalid ssoToken
				if(!shouldProceedWithRequest) {
					DIAGNOSTIC_LOGGER.debug("Invalid ssoToken:" + ssoTokenCookie.getValue());
					response.sendRedirect("/logout");
					shouldProceedWithRequest = true;
					/*ssoTokenCookie.setMaxAge(0);
					response.addCookie(ssoTokenCookie);*/
				}
			}

			Employee employee = (Employee) WebUtils.getSessionAttribute(request, WebConstants.SessionVariables.LOGGED_IN_USER);

			if(employee == null) {
				//Get session information
				final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(WebConstants.Modules.ORGANISATION_WEB_APPLICATION, WebConstants.Operations.DeviceAuthentication.READ));
				final DeviceAuthentication authenticatedSession = this.restTemplate.exchange(this.authenticationServiceURL + ssoTokenCookie.getValue(), HttpMethod.GET, entity, DeviceAuthentication.class).getBody();

				//Get employee information
				employee = this.restTemplate.exchange(this.employeeServiceURL + authenticatedSession.getEmployeeId(), HttpMethod.GET, entity, Employee.class).getBody();
				WebUtils.setSessionAttribute(request, WebConstants.SessionVariables.LOGGED_IN_USER, employee);
			}
		}

		return shouldProceedWithRequest;
	}

	private Cookie createCookie(final String cookieName, final HttpServletRequest request) {
		final DeviceAuthentication session = this.createDummySSOToken();
		final Cookie ssoTokenCookie = new Cookie(SSO_TOKEN, session.getSessionId());
		ssoTokenCookie.setMaxAge(this.sessionDuration);
		ssoTokenCookie.setSecure(true);
		/*ssoTokenCookie.setDomain("devops_organisation");
		ssoTokenCookie.setPath("devops_organisation");*/
		return ssoTokenCookie;
	}

	private DeviceAuthentication createDummySSOToken() {
		final DeviceAuthentication dummyAuthentication = new DeviceAuthentication(new BigDecimal(1), DeviceAuthenticationConstants.SessionConstants.CREATE_WEB_SESSION, DeviceAuthenticationConstants.SessionConstants.DUMMY_SIGNED_MESSAGE);
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(WebConstants.Modules.ORGANISATION_WEB_APPLICATION, WebConstants.Operations.DeviceAuthentication.SAVE), dummyAuthentication);
		final DeviceAuthentication createdSSOToken = this.restTemplate.exchange(this.authenticationServiceURL, HttpMethod.POST, entity, DeviceAuthentication.class).getBody();
		return createdSSOToken;
	}

	protected boolean isSSOTokenValid(final HttpServletRequest request) {
		boolean isSSOTokenValid = false;
		final Cookie cookie = this.getCookie(SSO_TOKEN, request);
		if(null != cookie && StringUtils.isNotEmpty(cookie.getValue())) {
			isSSOTokenValid = this.isSSOTokenValid(cookie.getValue());
		}
		return isSSOTokenValid;
	}

	private boolean isSSOTokenValid(final String sessionId) {
		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(WebConstants.Modules.ORGANISATION_WEB_APPLICATION, WebConstants.Operations.DeviceAuthentication.IS_VALID_SESSION));
		final boolean isSSOTokenValid = this.restTemplate.exchange(this.authenticationServiceURL + CommonConstants.Separators.URL_SEPARATOR + "isValidSessionId" + CommonConstants.Separators.URL_SEPARATOR + sessionId, HttpMethod.GET, entity, Boolean.class).getBody();

		return isSSOTokenValid;
	}

	private Cookie getCookie(final String cookieName, final HttpServletRequest request) {
		Cookie cookie = null;
		if(null != request.getCookies()) {
			for(final Cookie requestCookie : request.getCookies()) {
				if(requestCookie.getName().equals(cookieName)) {
					cookie = requestCookie;
					break;
				}
			}
		}
		return cookie;
	}
}