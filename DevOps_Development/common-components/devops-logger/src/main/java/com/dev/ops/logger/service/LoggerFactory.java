package com.dev.ops.logger.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.LogManager;
import org.slf4j.Logger;

public class LoggerFactory {

	private static ConcurrentMap<String, DiagnosticLogger> LOGGER_MAP = new ConcurrentHashMap<String, DiagnosticLogger>();
	private static ConcurrentMap<String, PerformanceLogger> PERFORMANCE_LOGGER_MAP = new ConcurrentHashMap<String, PerformanceLogger>();
	private static ConcurrentMap<String, ErrorLogger> ERROR_LOGGER_MAP = new ConcurrentHashMap<String, ErrorLogger>();

	public static DiagnosticLogger getDiagnosticLogger(final Class<?> clazz) {
		final DiagnosticLogger customLogger = LOGGER_MAP.get(clazz.getName());
		if(customLogger != null) {
			return customLogger;
		} else {
			org.apache.log4j.Logger log4jLogger;
			if(clazz.getName().equalsIgnoreCase(Logger.ROOT_LOGGER_NAME)) {
				log4jLogger = LogManager.getRootLogger();
			} else {
				log4jLogger = LogManager.getLogger(clazz);
			}
			final DiagnosticLogger newInstance = new DiagnosticLogger(log4jLogger);
			final DiagnosticLogger oldInstance = LOGGER_MAP.putIfAbsent(clazz.getName(), newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}

	public static PerformanceLogger getPerformanceLogger(final Class<?> clazz) {
		final PerformanceLogger performanceLogger = PERFORMANCE_LOGGER_MAP.get("performance." + clazz.getName());
		if(performanceLogger != null) {
			return performanceLogger;
		} else {
			org.apache.log4j.Logger log4jLogger;
			if(clazz.getName().equalsIgnoreCase(Logger.ROOT_LOGGER_NAME)) {
				log4jLogger = LogManager.getRootLogger();
			} else {
				log4jLogger = LogManager.getLogger("performance." + clazz.getName());
			}
			final PerformanceLogger newInstance = new PerformanceLogger(log4jLogger);
			final PerformanceLogger oldInstance = PERFORMANCE_LOGGER_MAP.putIfAbsent("performance." + clazz.getName(), newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}

	public static ErrorLogger getErrorLogger(final String className) {
		final ErrorLogger errorLogger = ERROR_LOGGER_MAP.get(className);
		if(errorLogger != null) {
			return errorLogger;
		} else {
			org.apache.log4j.Logger log4jLogger;
			if(className.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME)) {
				log4jLogger = LogManager.getRootLogger();
			} else {
				log4jLogger = LogManager.getLogger(className);
			}
			final ErrorLogger newInstance = new ErrorLogger(log4jLogger);
			final ErrorLogger oldInstance = ERROR_LOGGER_MAP.putIfAbsent(className, newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}
}