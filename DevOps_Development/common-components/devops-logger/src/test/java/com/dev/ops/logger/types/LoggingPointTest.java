package com.dev.ops.logger.types;

import junit.framework.Assert;

import org.junit.Test;

public class LoggingPointTest {

	@Test
	public void testEnum() {
		Assert.assertEquals(LoggingPoint.START, LoggingPoint.valueOf("START"));
		Assert.assertEquals(LoggingPoint.END, LoggingPoint.valueOf("END"));
	}
}
