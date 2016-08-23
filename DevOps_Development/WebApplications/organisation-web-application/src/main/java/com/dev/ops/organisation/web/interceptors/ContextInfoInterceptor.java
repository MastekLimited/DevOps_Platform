package com.dev.ops.organisation.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.dev.ops.common.constants.CommonConstants;
import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.thread.local.ContextThreadLocal;
import com.dev.ops.employee.service.domain.Employee;
import com.dev.ops.organisation.web.constants.WebConstants;

@Component
public class ContextInfoInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object object, final Exception exception) throws Exception {

	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object, final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object) throws Exception {
		final boolean shouldProceedWithRequest = true;
		final Employee employee = (Employee) WebUtils.getSessionAttribute(request, WebConstants.SessionVariables.LOGGED_IN_USER);
		final ContextInfo contextInfo = new ContextInfo(WebConstants.Modules.ORGANISATION_WEB_APPLICATION, WebConstants.Operations.INITIALIZE_OPERATION);

		if(employee != null) {
			contextInfo.setTransactionRequestedByUserId(employee.getEmployeeId().toString());
			contextInfo.setTransactionRequestedByUsername(employee.getFirstName() + CommonConstants.Separators.BLANK_SPACE_SEPARATOR + employee.getLastName());
		} else {
			contextInfo.setTransactionRequestedByUserId(WebConstants.AdminUser.USER_ID);
			contextInfo.setTransactionRequestedByUsername(WebConstants.AdminUser.USERNAME);
		}

		ContextThreadLocal.set(contextInfo);
		return shouldProceedWithRequest;
	}
}