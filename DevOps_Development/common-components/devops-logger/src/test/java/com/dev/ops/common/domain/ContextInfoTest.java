package com.dev.ops.common.domain;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class ContextInfoTest {

	@Test
	public void testDomainObject() {
		//Test default constructor.
		ContextInfo contextInfo = new ContextInfo();
		Assert.assertNotNull("Context info object should not be null", contextInfo);
		Assert.assertNotNull("Event data object should be initialised", contextInfo.getEventData());
		Assert.assertNotNull("toString() method should not return null value.", contextInfo.toString());

		//Test parameterised constructor
		contextInfo = new ContextInfo("Logger", "Test");
		Assert.assertNotNull("Context info object should not be null", contextInfo);
		Assert.assertNotNull("Event data object should be initialised", contextInfo.getEventData());
		Assert.assertNotNull("toString() method should not return null value.", contextInfo.toString());

		contextInfo.setDeviceId("deviceId");
		contextInfo.setRequestId("requestId");
		contextInfo.setSessionId("sessionId");
		contextInfo.setSsoTicket("ssoTicket");
		contextInfo.addEventData("key", "value");
		contextInfo.addEventData("key1", "value1");

		Assert.assertEquals("Logger", contextInfo.getModuleName());
		Assert.assertEquals("Test", contextInfo.getOperation());
		Assert.assertEquals("deviceId", contextInfo.getDeviceId());
		Assert.assertEquals("requestId", contextInfo.getRequestId());
		Assert.assertEquals("sessionId", contextInfo.getSessionId());
		Assert.assertEquals("ssoTicket", contextInfo.getSsoTicket());

		Assert.assertNotNull("Event data object should be initialised", contextInfo.getEventData());
		Assert.assertNotNull("toString() method should not return null value.", contextInfo.toString());
	}

	@Test
	public void testToContextInfo() throws IOException {
		final ContextInfo inputContextInfo = new ContextInfo("Logger", "Test");
		inputContextInfo.setDeviceId("deviceId");
		inputContextInfo.setRequestId("requestId");
		inputContextInfo.setSessionId("sessionId");
		inputContextInfo.setSsoTicket("ssoTicket");
		inputContextInfo.addEventData("key", "value");
		inputContextInfo.addEventData("key1", "value1");

		final ContextInfo contextInfo = ContextInfo.toContextInfo(inputContextInfo.toString());
		Assert.assertEquals(inputContextInfo.toString(), contextInfo.toString());
	}
}