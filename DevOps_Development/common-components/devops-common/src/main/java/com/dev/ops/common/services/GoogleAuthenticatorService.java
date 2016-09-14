package com.dev.ops.common.services;

import org.springframework.stereotype.Component;

import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Component
public class GoogleAuthenticatorService {

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(GoogleAuthenticatorService.class);
	private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

	/*public static void main(String[] args) {
		GoogleAuthenticatorService authenticatorService = new GoogleAuthenticatorService();
		String authenticationKey = authenticatorService.generateAuthenticationKey();
		System.out.println(authenticationKey);
		int verificationCode = 7687678;
		System.out.println("isAuthorized:" + authenticatorService.authenticateCredentials("22S4O5PE37MMRB5E", verificationCode));
	}*/

	public String generateAuthenticationKey() {
		final GoogleAuthenticatorKey key = this.googleAuthenticator.createCredentials();
		final String generatedKey = key.getKey();
		DIAGNOSTIC_LOGGER.debug("Google authentication generated key " + generatedKey);
		return generatedKey;
	}

	public boolean authenticateCredentials(final String authenticationKey, final int verificationCode) {
		return this.googleAuthenticator.authorize(authenticationKey, verificationCode);
	}
}