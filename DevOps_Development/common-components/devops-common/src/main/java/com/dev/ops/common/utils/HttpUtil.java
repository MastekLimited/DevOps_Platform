package com.dev.ops.common.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.thread.local.ContextThreadLocal;

public class HttpUtil {

	public static HttpEntity<?> getHeaders() {
		final ContextInfo contextInfo = ContextThreadLocal.get();
		final Object entity = null;
		return getEntityWithHeaders(entity, contextInfo);
	}

	public static HttpEntity<?> getHeaders(final ContextInfo contextInfo, final Object entityToSubmintInRequest) {
		return getEntityWithHeaders(entityToSubmintInRequest, contextInfo);
	}

	public static HttpEntity<?> getEntityWithHeaders(final ContextInfo contextInfo) {
		final Object entity = null;
		return getEntityWithHeaders(entity, contextInfo);
	}

	public static HttpEntity<?> getEntityWithHeaders(final Object entityToSubmintInRequest) {
		final ContextInfo contextInfo = ContextThreadLocal.get();
		return getEntityWithHeaders(entityToSubmintInRequest, contextInfo);
	}

	public static HttpEntity<?> getEntityWithHeaders(final ContextInfo contextInfo, final Object entityToSubmintInRequest) {
		return getEntityWithHeaders(entityToSubmintInRequest, contextInfo);
	}

	public static HttpEntity<?> getEntityWithHeaders(final String operation, final Object entityToSubmintInRequest) {
		final ContextInfo contextInfo = ContextThreadLocal.get();
		contextInfo.setOperation(operation);
		return getEntityWithHeaders(entityToSubmintInRequest, contextInfo);
	}

	public static HttpEntity<?> getEntityWithHeaders(final String moduleName, final String operation, final Object entityToSubmintInRequest) {
		final ContextInfo contextInfo = ContextThreadLocal.get();
		contextInfo.setModuleName(moduleName);
		contextInfo.setOperation(operation);

		return getEntityWithHeaders(entityToSubmintInRequest, contextInfo);
	}

	private static HttpEntity<?> getEntityWithHeaders(final Object entityToSubmintInRequest, final ContextInfo contextInfo) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("context", contextInfo.toString());

		HttpEntity<?> httpEntity = null;

		if(entityToSubmintInRequest == null) {
			httpEntity = new HttpEntity<String>(headers);
		} else {
			httpEntity = new HttpEntity<>(entityToSubmintInRequest, headers);
		}
		return httpEntity;
	}
}
