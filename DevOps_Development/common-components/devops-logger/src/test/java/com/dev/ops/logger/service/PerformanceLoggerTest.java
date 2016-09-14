package com.dev.ops.logger.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.logger.types.LoggingPoint;

// TODO:[Krishna]:Remove assertTrue(true) statements.
public class PerformanceLoggerTest {
	private static PerformanceLogger PERFORMANCE_LOGGER = LoggerFactory.getPerformanceLogger(PerformanceLoggerTest.class);
	private ContextInfo contextInfo;

	protected void setUp() throws Exception {
		this.contextInfo = new ContextInfo("Sample", "Sample");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testPerformance() {
		try {
			PERFORMANCE_LOGGER.log(LoggingPoint.START, "", "", "", "test performance logger");
			assertTrue(true);
		} catch(final Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testPerformanceContextInfo() {
		PERFORMANCE_LOGGER.log(LoggingPoint.START, this.contextInfo);
	}

	@Test
	public void testPerformanceContextInfoEmptyTest() {
		PERFORMANCE_LOGGER.log(LoggingPoint.START, new ContextInfo());
	}

	@Test
	public void testPerformanceContextInfoNull() {
		PERFORMANCE_LOGGER.log(LoggingPoint.START, null);
	}
}
