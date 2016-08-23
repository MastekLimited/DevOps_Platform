package com.dev.ops.common.service.exception;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.manager.ExceptionManager;
import com.dev.ops.exception.manager.impl.DefaultExceptionManager;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.WrappedException;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.exceptions.impl.WrappedSystemException;

public final class ExceptionUtil {

	private static ExceptionManager exceptionManager = DefaultExceptionManager.getExceptionManager();

	public interface ExceptionCodes {
		public interface Database {
			String PERSISTENCE_EXCEPTION = "DATABASE_PERSISTENCE_EXCEPTION";
		}

		public interface Connection {
			String CONNECTION_EXCEPTION = "CONNECTION_EXCEPTION";
		}
	}

	private ExceptionUtil() {
	}

	public static DefaultWrappedException createException(final Severity severity, final DefaultWrappedException exception, final ContextInfo contextInfo, final boolean printStackTrace) {
		logException(severity, contextInfo, printStackTrace, exception);
		return exception;
	}

	public static WrappedSystemException createException(final Severity severity, final WrappedSystemException exception, final ContextInfo contextInfo, final boolean printStackTrace) {
		logException(severity, contextInfo, printStackTrace, exception);
		return exception;
	}

	public static Exception createException(final String exceptionId, final Severity severity, final Exception exception, final ContextInfo contextInfo, final boolean printStackTrace, final Object[] param) {
		final DefaultWrappedException wrappedException = new DefaultWrappedException(exceptionId, exception, param);
		logException(severity, contextInfo, printStackTrace, wrappedException);
		return wrappedException;
	}

	public static void logException(final Severity severity, final ContextInfo contextInfo, final boolean printStackTrace, final WrappedException exception) {
		exceptionManager.logErrorEvent(exception, contextInfo, severity, printStackTrace);
	}
}