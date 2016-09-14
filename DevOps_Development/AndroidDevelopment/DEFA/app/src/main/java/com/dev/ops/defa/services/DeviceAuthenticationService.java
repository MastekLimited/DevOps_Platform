package com.dev.ops.defa.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.utils.HttpUtil;
import com.dev.ops.defa.utils.DeviceUtility;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;

import org.spongycastle.cert.jcajce.JcaCertStore;
import org.spongycastle.cms.CMSProcessableByteArray;
import org.spongycastle.cms.CMSSignedData;
import org.spongycastle.cms.CMSSignedDataGenerator;
import org.spongycastle.cms.CMSTypedData;
import org.spongycastle.cms.SignerInfoGenerator;
import org.spongycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.spongycastle.util.Store;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import com.dev.ops.defa.notifiers.BroadcastNotifier;
import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;
import com.dev.ops.employee.service.domain.Employee;

/**
 * Created by Rohan Jayraj Mohite on 07/01/2016.
 */
public class DeviceAuthenticationService extends IntentService {

    protected static final String TAG =DeviceAuthenticationService.class.toString();
    private BroadcastNotifier mBroadcaster = null;
    private RestTemplate restTemplate = null;
    private String intentServiceErrorStatus = null;
    private Device device;

    public DeviceAuthenticationService() {
        super(TAG);
        device = Device.getInstance();
        mBroadcaster = new BroadcastNotifier(this);
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    @Override
    protected void onHandleIntent(Intent intent){

        try{
            SharedPreferences preferences = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE);
            String deviceRegistrationId = preferences.getString(CommonPreferences.SharedPreferences.Key.REGISTRATION_ID, null);
            if (deviceRegistrationId != null) {
                device.setRegistrationId(deviceRegistrationId);
                mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.SERVICE_STARTED, device.getBroadcastAction(), intentServiceErrorStatus);
                if(isDeviceAuthenticated()){
                    mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.AUTH_VALIDATION_SUCCESS, device.getBroadcastAction(), intentServiceErrorStatus);
                }else{
                    mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.AUTH_VALIDATION_FAILED, device.getBroadcastAction(), intentServiceErrorStatus);
                }
                mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.SERVICE_COMPLETED, device.getBroadcastAction(), intentServiceErrorStatus);
            } else {
                intentServiceErrorStatus = "No tokens are installed. Contact your system administrator.";
                Log.e(TAG,intentServiceErrorStatus);
                mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.SERVICE_ERROR, device.getBroadcastAction(), intentServiceErrorStatus );
            }

        } catch (HttpServerErrorException e) {
            intentServiceErrorStatus = e.getResponseBodyAsString();
            mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.SERVICE_ERROR, device.getBroadcastAction(),intentServiceErrorStatus );
            Log.e(TAG, e.getResponseBodyAsString());
        } catch (Exception e) {
            intentServiceErrorStatus = e.getMessage();
            Log.e(TAG, e.getMessage());
            mBroadcaster.broadcastIntentWithState(CommonPreferences.ProgressStatus.Login.SERVICE_ERROR, device.getBroadcastAction(), intentServiceErrorStatus );
        }
    }

    private boolean isDeviceAuthenticated()throws Exception{
        boolean authValidated = false;
        String encodedPKCS7Request = null;
        KeyStore.PrivateKeyEntry keyEntry = DeviceUtility.getKeyEntry();
        String challenge = getChallenge();

        if (keyEntry != null && DeviceUtility.isCertificateAvailableInKeystore(keyEntry)) {
            DeviceUtility.loadCertificates(keyEntry, device);
            encodedPKCS7Request = signChallenge(challenge);
            if (encodedPKCS7Request != null) {
                invokeAuthenticationService(encodedPKCS7Request);

                if (device.getSessionId()!=null) {
                    authValidated = true;
                }
            }
        }
        return authValidated;
    }

    @NonNull
    private String getChallenge() {
        String challenge = device.getEmployeeId();
        challenge = challenge + CommonPreferences.Separators.DOLLAR;
        challenge = challenge + device.getRegistrationId();
        challenge = challenge + CommonPreferences.Separators.DOLLAR;
        challenge = challenge + device.getAndroidDeviceId();
        challenge = challenge + CommonPreferences.Separators.DOLLAR;
        challenge = challenge + device.getApplicationId();
        challenge = challenge + CommonPreferences.Separators.DOLLAR;
        challenge = challenge + device.getOtpValue();

        return challenge;
    }

    private String signChallenge(String challenge) throws Exception {

        String encodedCertificate = device.getEncodedCertificate();
        PrivateKey privateKey = device.getRsaPrivateKey();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(encodedCertificate.getBytes()));

        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(CommonPreferences.SignatureAlgorithm.SHA1_WITH_RSA).setProvider(CommonPreferences.Provider.ANDROID_OPEN_SSL);
        ContentSigner sha1Signer = jcaContentSignerBuilder.build(privateKey);

        JcaDigestCalculatorProviderBuilder jcaDigestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder().setProvider(CommonPreferences.Provider.SPONGY_CASTLE);
        DigestCalculatorProvider digestCalculatorProvider = jcaDigestCalculatorProviderBuilder.build();

        JcaSignerInfoGeneratorBuilder jcaSignerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);
        jcaSignerInfoGeneratorBuilder.setDirectSignature(true);
        SignerInfoGenerator signerInfoGenerator = jcaSignerInfoGeneratorBuilder.build(sha1Signer, x509Certificate);

        List certificateList = new ArrayList();
        certificateList.add(x509Certificate);
        Store certificateStore = new JcaCertStore(certificateList);
        CMSSignedDataGenerator cmsSignedDataGenerator = new CMSSignedDataGenerator();
        cmsSignedDataGenerator.addSignerInfoGenerator(signerInfoGenerator);
        cmsSignedDataGenerator.addCertificates(certificateStore);

        byte[] content = challenge.getBytes();
        CMSTypedData msgToSigned = new CMSProcessableByteArray(content);
        CMSSignedData signedData = cmsSignedDataGenerator.generate(msgToSigned, true);
        signedData.toASN1Structure().getEncoded("DER");

        String signedContent = new String(Base64.encode(signedData.getEncoded(), 0));
        return signedContent;
    }

    protected void invokeAuthenticationService(String signedMessage) {
        // The URL for making the POST request
        final String url = device.getAuthenticationServiceURL();

        DeviceAuthentication deviceAuthentication = new DeviceAuthentication();

        deviceAuthentication.setRegistrationId(device.getRegistrationId());
        deviceAuthentication.setSignedMessage(signedMessage);

        final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.AUTHENTICAtION), deviceAuthentication);
        final DeviceAuthentication responseDeviceAuthentication = this.restTemplate.exchange(url, HttpMethod.POST, entity, DeviceAuthentication.class).getBody();

        device.setSessionId(responseDeviceAuthentication.getSessionId());

        SharedPreferences.Editor editor = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE).edit();
        editor.putString(CommonPreferences.SharedPreferences.Key.SESSION_ID, device.getSessionId());
        editor.commit();

        getEmployeeDetails();
    }

    protected void getEmployeeDetails() {
        String url = device.getEmployeeServiceURL()  + device.getEmployeeId();

        final HttpEntity<?> entity = HttpUtil.getEntityWithHeaders(new ContextInfo(CommonPreferences.ApplicationConstants.APPLICATION_NAME, CommonPreferences.Operation.IS_SESSION_VALID));
        Employee employee = this.restTemplate.exchange(url, HttpMethod.GET, entity, Employee.class).getBody();

        device.setEmployeeName(employee.getFirstName() +" "+ employee.getLastName());

        SharedPreferences.Editor editor = getSharedPreferences(CommonPreferences.SharedPreferences.NAME, MODE_PRIVATE).edit();
        editor.putString(CommonPreferences.SharedPreferences.Key.EMPLOYEE_NAME, device.getEmployeeName());
        editor.commit();
    }
}