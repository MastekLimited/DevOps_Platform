package com.dev.ops.exception.types;

import junit.framework.Assert;

import org.junit.Test;

public class SeverityTest {

	@Test
	public void testEnum() {
		Assert.assertEquals(Severity.ERROR, Severity.valueOf("ERROR"));
		Assert.assertEquals(Severity.FATAL, Severity.valueOf("FATAL"));
	}
}
