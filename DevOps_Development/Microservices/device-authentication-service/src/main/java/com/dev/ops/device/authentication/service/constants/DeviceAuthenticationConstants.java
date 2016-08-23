package com.dev.ops.device.authentication.service.constants;

public interface DeviceAuthenticationConstants {
	interface ExceptionCodes {
		String CERTIFICATE_VALIDATION_EXCEPTION = "CERTIFICATE_VALIDATION_EXCEPTION";
	}

	interface SessionConstants {
		String CREATE_WEB_SESSION = "CREATE_WEB_SESSION";

		//TODO: Implement logic to set employeeId and other required attributes in this signed message.
		String DUMMY_SIGNED_MESSAGE = "MIAGCSqGSIb3DQEHAqCAMIACAQExCzAJBgUrDgMCGgUAMIAGCSqGSIb3DQEHAaCAJIAEIzEkbnVsbCQxMDM5ODI5YjRiYjBhNmI4JG51bGwkNDYzNTY3AAAAAAAAoIAwggK7MIICJKADAgECAgYBUhvv6iEwDQYJKoZIhvcNAQELBQAwIjELMAkGA1UEBhMCR0IxEzARBgNVBAMMCkF1dGhTZXJ2ZXIwHhcNMTYwMTA3MTE1NDEzWhcNMTcwMTA2MTE1NDEzWjAkMREwDwYDVQQDDAgxOENFRDYwNTEPMA0GA1UECgwGUGVvcGxlMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDr0yCN8drTZ4Yrpj6AV0E1ZcpjqGLVesofupsIxxYKkcy+Kd9CV5+Nmlfe116kixEiJN5VVZ8kzsJYGYSwyNesA1SzHZwRpM/flwf92uUDmjiSedmx0twumVVumxhhsAnUpxWfaKEH9ak8BpkM38fQnD/PeabK/AKBpwRQi5/hUwIDAQABo4H5MIH2MIGeBgNVHSMEgZYwgZOAFJ0EIE0z3tUBy0UuCPy2G9y5Il/3oXOkcTBvMQswCQYDVQQGEwJHQjEfMB0GA1UECgwWWW91ciBPcmdhbmlzYXRpb24gTmFtZTEgMB4GA1UECwwXQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkxHTAbBgNVBAMMFFlvdXIgT3JnYW5pc2F0aW9uIENBggYBR/3O8fUwHQYDVR0OBBYEFLpyjNgQOBUjVaakKEoNmmQ6T497MAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgWgMBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMBMA0GCSqGSIb3DQEBCwUAA4GBABRKfFOhUjh+SqRzeXmjJk5aGhsOG49XGLUSl6Qjm/B2LUFO+WBTdhXmkpC5oeN40nwDUSnsl3iZ/AtQkuxDUB6qJFwLqzcc8MBDn4+VsbFedIpyt43JB1MXiaiMCBoXihCQ5bgWO7+KnY0VzcJ0eFXpR0ct/iBtPZ/gP1BCknmzAAAxgdEwgc4CAQEwLDAiMQswCQYDVQQGEwJHQjETMBEGA1UEAwwKQXV0aFNlcnZlcgIGAVIb7+ohMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYB6mbqbwhcnqKdM0M7yXHWuTwp/SqW9TDEaj48o2+y4O/mnwZv0Q+AC5qYT/2ODZni8StHzbqCmABaZTgGPRoruaWazwwF8CCJIp94z6tC0WC7vt9WWtjHam2vvlBRgDZmHIbd3dV5BwHm89WRfiG9pt8fIpfTCRu8aWXY9yRztIQAAAAAAAA==";
	}
}