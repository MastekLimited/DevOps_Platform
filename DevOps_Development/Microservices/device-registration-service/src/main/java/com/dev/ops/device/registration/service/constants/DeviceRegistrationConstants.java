package com.dev.ops.device.registration.service.constants;

public interface DeviceRegistrationConstants {

	interface CertificateTypes {
		String PKCS12 = "PKCS12";
	}

	interface RSAAlgorithms {
		String SHA256_WITH_RSA_ENCRYPTION = "SHA256WithRSAEncryption";
	}

	interface ExceptionCodes {
		String CSR_VALIDATION_EXCEPTION = "CSR_VALIDATION_EXCEPTION";
	}
}