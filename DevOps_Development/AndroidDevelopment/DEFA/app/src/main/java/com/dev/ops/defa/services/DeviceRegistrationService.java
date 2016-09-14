
package com.dev.ops.defa.services;

import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.util.PrivateKeyFactory;
import org.spongycastle.openssl.PEMWriter;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.bc.BcRSAContentSignerBuilder;
import org.spongycastle.pkcs.PKCS10CertificationRequest;
import org.spongycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.spongycastle.util.io.pem.PemObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.device.registration.service.domain.DeviceRegistration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.dev.ops.defa.notifiers.BroadcastNotifier;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;

public class DeviceRegistrationService extends IntentService {

	protected static final String TAG = DeviceRegistrationService.class.getName();
	private BroadcastNotifier broadcastNotifier = null;
	private RestTemplate restTemplate = null;
	private String intentServiceErrorStatus = null;
	private Device device;

	public DeviceRegistrationService() {
		super(TAG);
        //device = new Device();
		device = Device.getInstance();
        broadcastNotifier = new BroadcastNotifier(this);
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.SERVICE_STARTED, device.getBroadcastAction(), intentServiceErrorStatus);
                registrationService();
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.SERVICE_COMPLETED, device.getBroadcastAction(), intentServiceErrorStatus);

				SharedPreferences.Editor editor = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE).edit();
				editor.putString(CommonPreferences.SharedPreferences.Key.REGISTRATION_ID, device.getRegistrationId());
				editor.commit();

		} catch (HttpServerErrorException e) {
                intentServiceErrorStatus = e.getResponseBodyAsString();
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.SERVICE_ERROR, device.getBroadcastAction(), intentServiceErrorStatus);
                Log.e(TAG, e.getResponseBodyAsString());
		} catch (Exception e) {
                intentServiceErrorStatus = e.getMessage();
                Log.e(TAG, e.getMessage());
                broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.SERVICE_ERROR, device.getBroadcastAction(), intentServiceErrorStatus);
		}
	}

	private void registrationService()throws Exception{

		KeyUsage keyUsage = new KeyUsage(KeyUsage.digitalSignature);
		int keySize = 1024;

        broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.GENERATE_KEYPAIRS, device.getBroadcastAction(), intentServiceErrorStatus);
        Thread.sleep(2000);
		generateKeyPair(keySize);

		broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.GENERATE_CSR, device.getBroadcastAction(), intentServiceErrorStatus);
		Thread.sleep(2000);

		getCertificateSigningRequest(keyUsage);

		if (device.getEncodedCSR() != null) {
			broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.SUBMIT_ENROLLMENT_REQUEST, device.getBroadcastAction(), intentServiceErrorStatus);
			invokeRegistrationService();
			broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.RECEIVED_ENROLLMENT_REQUEST, device.getBroadcastAction(), intentServiceErrorStatus);
		}else {
			throw new RuntimeException("Failed to generate CSR");
		}

		if (device.getEncodedCertificate() != null) {
			saveCertificateAndKeysToKeyStore();
			broadcastNotifier.broadcastIntentWithState(CommonPreferences.ProgressStatus.Registration.STORE_CERTIFICATE, device.getBroadcastAction(), intentServiceErrorStatus);
		}else {
			throw new RuntimeException("Failed to get certificate from server");
		}
	}

	private void generateKeyPair(int keySize) throws NoSuchAlgorithmException {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CommonPreferences.KeypairType.RSA);
		keyPairGenerator.initialize(keySize);
		KeyPair keyPair = keyPairGenerator.genKeyPair();

		byte[] privateKey = keyPair.getPrivate().getEncoded();
		byte[] publicKey = keyPair.getPublic().getEncoded();

		device.setRsaKeyPair(keyPair);
		device.setPrivateKey(privateKey);
		device.setPublicKey(publicKey);
	}

	private void getCertificateSigningRequest(KeyUsage keyUsage) throws IOException, OperatorCreationException {
		String principal = CommonPreferences.CertificateConstants.COMMON_NAME + device.getRegistrationId() + CommonPreferences.CertificateConstants.ORGANISATION_NAME + CommonPreferences.CertificateConstants.PEOPLE;
		AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(device.getRsaKeyPair().getPrivate().getEncoded());
		AlgorithmIdentifier signatureAlgorithm = new DefaultSignatureAlgorithmIdentifierFinder().find(CommonPreferences.SignatureAlgorithm.SHA1_WITH_RSA);
		AlgorithmIdentifier digestAlgorithm = new DefaultDigestAlgorithmIdentifierFinder().find(signatureAlgorithm);

		ContentSigner signer = new BcRSAContentSignerBuilder(signatureAlgorithm, digestAlgorithm).build(privateKey);

		PKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(new X500Name(principal), device.getRsaKeyPair().getPublic());
		ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
		extensionsGenerator.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
		extensionsGenerator.addExtension(Extension.keyUsage, true, keyUsage);
		// csrBuilder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, extensionsGenerator.generate());

		csrBuilder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_challengePassword, new DERPrintableString(new String(device.getSecretChallenge())));

		PKCS10CertificationRequest csr = csrBuilder.build(signer);

		PemObject pemObject = new PemObject("certificate REQUEST", csr.getEncoded());
		StringWriter strWriter = new StringWriter();
		PEMWriter pemWriter = new PEMWriter(strWriter);
		pemWriter.writeObject(pemObject);
		pemWriter.close();

		StringBuffer sb = strWriter.getBuffer();
		String encodedCertificateRequest = sb.toString();
		strWriter.close();

		encodedCertificateRequest = encodedCertificateRequest.replace("\n","");

        device.setEncodedCSR(encodedCertificateRequest);
	}

	private void saveCertificateAndKeysToKeyStore() throws Exception {
		byte [] decodedCertificate = Base64.decode(device.getEncodedCertificate(), 0);
		X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decodedCertificate));

		KeyStore keyStore = KeyStore.getInstance(CommonPreferences.KeyStore.NAME);
		keyStore.load(null);
		keyStore.setKeyEntry(CommonPreferences.KeyStore.DEFA_ALIAS, device.getRsaKeyPair().getPrivate(), null, new java.security.cert.Certificate[]{x509Certificate});
	}

	protected void invokeRegistrationService() {

        // The URL for making the POST request
		final String url = device.getRegistrationServiceURL() + CommonPreferences.ServiceURL.REGISTRATION_SERVER_URL;

		DeviceRegistration deviceRegistration = new DeviceRegistration();
		deviceRegistration.setRegistrationId(device.getRegistrationId());
        deviceRegistration.setDeviceId(device.getAndroidDeviceId());
		deviceRegistration.setCsr(device.getEncodedCSR());

		final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.REGISTRATION), deviceRegistration);
		final DeviceRegistration responseDeviceRegistration = this.restTemplate.exchange(url, HttpMethod.POST, entity, DeviceRegistration.class).getBody();

        device.setEncodedCertificate(responseDeviceRegistration.getCertificate());
    }
}