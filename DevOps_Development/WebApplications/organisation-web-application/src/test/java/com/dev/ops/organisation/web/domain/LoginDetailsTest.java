package com.dev.ops.organisation.web.domain;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;

public class LoginDetailsTest {

	@Test
	public void testDomainObject() {
		final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		final LoginDetails loginDetails = new LoginDetails("123412341234");
		loginDetails.setLoggedInTime(currentTimestamp);
		Assert.assertNotNull(loginDetails);
		Assert.assertEquals("123412341234", loginDetails.getUserUUID());
		Assert.assertEquals(currentTimestamp, loginDetails.getLoggedInTime());
		Assert.assertEquals(loginDetails, loginDetails);

		loginDetails.setUserUUID("567856785678");
		Assert.assertEquals("Mocked and returned objects should be equal.", "567856785678", loginDetails.getUserUUID());
		Assert.assertNotNull("toString() method should not return null value.", loginDetails.toString());
	}

	@Test
	public void testEquals() {
		final LoginDetails loginDetails = new LoginDetails("123412341234");
		//Equal
		Assert.assertEquals(loginDetails, loginDetails);
		Assert.assertEquals(new LoginDetails("123412341234"), loginDetails);
		Assert.assertTrue(new LoginDetails(null).equals(new LoginDetails(null)));

		//Unequal
		Assert.assertFalse(loginDetails.equals(null));
		Assert.assertFalse(loginDetails.equals(new Object()));
		Assert.assertFalse(loginDetails.equals(new LoginDetails("12341241234")));
		Assert.assertFalse(new LoginDetails(null).equals(loginDetails));
		Assert.assertFalse(loginDetails.equals(new LoginDetails("12341241234")));
	}

	@Test
	public void testHashCode() {
		final LoginDetails loginDetails = new LoginDetails("123412341234");
		Assert.assertNotNull(loginDetails.hashCode());
		Assert.assertNotNull(new LoginDetails().hashCode());
	}
}