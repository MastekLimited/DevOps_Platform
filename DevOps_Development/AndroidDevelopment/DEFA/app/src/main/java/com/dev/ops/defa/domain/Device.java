package com.dev.ops.defa.domain;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Rohan Jayraj Mohite on 24/12/2015.
 */
public class Device {

    private static Device instance = null;
    private Device() {
        // Exists only to defeat instantiation.
    }
    public static Device getInstance() {
        if(instance == null) {
            instance = new Device();
        }
        return instance;
    }

    private String androidDeviceId;
    private String registrationId;
    private String applicationId;
    private String secretChallenge;
    private String broadcastAction;
    private String encodedCSR;
    private String encodedCertificate;
    private byte[] privateKey;
    private byte[] publicKey;
    private KeyPair rsaKeyPair;
    private PublicKey rsaPublicKey;
    private PrivateKey rsaPrivateKey;
    private String otpValue;
    private String employeeId;
    private String sessionId;
    private boolean isSessionValid;
    private String organisationWebURL;
    private String authenticationServiceURL;
    private String registrationServiceURL;
    private String employeeServiceURL;
    private String employeeName;

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSecretChallenge() {
        return secretChallenge;
    }

    public void setSecretChallenge(String secretChallenge) {
        this.secretChallenge = secretChallenge;
    }

    public String getBroadcastAction() {
        return broadcastAction;
    }

    public void setBroadcastAction(String broadcastAction) {
        this.broadcastAction = broadcastAction;
    }

    public String getEncodedCSR() {
        return encodedCSR;
    }

    public void setEncodedCSR(String encodedCSR) {
        this.encodedCSR = encodedCSR;
    }

    public String getEncodedCertificate() {
        return encodedCertificate;
    }

    public void setEncodedCertificate(String encodedCertificate) {
        this.encodedCertificate = encodedCertificate;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public KeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

    public void setRsaKeyPair(KeyPair rsaKeyPair) {
        this.rsaKeyPair = rsaKeyPair;
    }

    public PublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(PublicKey rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public PrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(PrivateKey rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(String otpValue) {
        this.otpValue = otpValue;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSessionValid() {
        return isSessionValid;
    }

    public void setIsSessionValid(boolean isSessionValid) {
        this.isSessionValid = isSessionValid;
    }

    public String getOrganisationWebURL() {
        return organisationWebURL;
    }

    public void setOrganisationWebURL(String organisationWebURL) {
        this.organisationWebURL = organisationWebURL;
    }

    public String getAuthenticationServiceURL() {
        return authenticationServiceURL;
    }

    public void setAuthenticationServiceURL(String authenticationServiceURL) {
        this.authenticationServiceURL = authenticationServiceURL;
    }

    public String getRegistrationServiceURL() {
        return registrationServiceURL;
    }

    public void setRegistrationServiceURL(String registrationServiceURL) {
        this.registrationServiceURL = registrationServiceURL;
    }

    public String getEmployeeServiceURL() {
        return employeeServiceURL;
    }

    public void setEmployeeServiceURL(String employeeServiceURL) {
        this.employeeServiceURL = employeeServiceURL;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
