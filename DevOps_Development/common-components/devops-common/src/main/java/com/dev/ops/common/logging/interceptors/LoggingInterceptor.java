package com.dev.ops.common.logging.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.logger.service.PerformanceLogger;
import com.dev.ops.logger.types.LoggingPoint;

@Aspect
@Component
public class LoggingInterceptor {

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(LoggingInterceptor.class);

	private static final PerformanceLogger PERFORMANCE_LOGGER = LoggerFactory.getPerformanceLogger(LoggingInterceptor.class);

	@Before("execution(* com.dev.ops..*.*(..)) && !execution(* com.dev.ops.common.orika..*.*(..)) && !execution(* com.dev.ops.organisation.web.configurations..*.*(..)) && !execution(* com.dev.ops.organisation.web.interceptors.ContextInfoInterceptor..*(..))")
	public void logMethodStart(final JoinPoint joinPoint) {
		final StringBuilder logMessage = this.getMethodSignature(joinPoint);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), null, new Object[] {logMessage});
	}

	@After("execution(* com.dev.ops..*.*(..)) && !execution(* com.dev.ops.common.orika..*.*(..)) && !execution(* com.dev.ops.organisation.web.configurations..*.*(..)) && !execution(* com.dev.ops.organisation.web.interceptors.ContextInfoInterceptor..*(..))")
	public void addContextInfoToThreadLocal(final JoinPoint joinPoint) {
		final StringBuilder logMessage = this.getMethodSignature(joinPoint);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), null, new Object[] {logMessage});
	}

	@Around("execution(* com.dev.ops..controllers..*.*(..))")
	public Object logTimeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//TODO: JenkinsBuild: Constraint Violation.
		/*Object retVal = null;
		try {
			retVal = joinPoint.proceed();
		}catch(Throwable e) {
			throw e;
		}*/

		final Object retVal = joinPoint.proceed();

		stopWatch.stop();

		final StringBuilder logMessage = this.getMethodSignature(joinPoint);
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		PERFORMANCE_LOGGER.log(logMessage.toString());
		return retVal;
	}

	private StringBuilder getMethodSignature(final JoinPoint joinPoint) {
		final StringBuilder logMessage = new StringBuilder();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");

		final Object[] args = joinPoint.getArgs();
		for(final Object arg : args) {
			if(null != arg) {
				logMessage.append(arg.getClass().getSimpleName()).append(", ");
			}
		}

		if(args.length > 0) {
			logMessage.delete(logMessage.length() - 2, logMessage.length());
		}

		logMessage.append(")");
		return logMessage;
	}
}