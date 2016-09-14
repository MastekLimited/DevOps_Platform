package com.dev.ops.defa.utils;

import android.util.Base64;

import com.dev.ops.defa.constants.CommonPreferences;
import com.dev.ops.defa.domain.Device;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

/**
 * Created by Rohan Jayraj Mohite on 11/01/2016.
 */
public class DeviceUtility {

    public static KeyStore.PrivateKeyEntry getKeyEntry() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance(CommonPreferences.KeyStore.NAME);
        keyStore.load(null);
        return (KeyStore.PrivateKeyEntry) keyStore.getEntry(CommonPreferences.KeyStore.DEFA_ALIAS, null);
    }

    public static boolean isCertificateAvailableInKeystore(KeyStore.PrivateKeyEntry keyEntry)throws Exception{
        boolean isCertificateAvailable = false;
        // in another part of the app, access the keys
        PrivateKey privateKey = keyEntry.getPrivateKey();
        PublicKey publicKey = keyEntry.getCertificate().getPublicKey();
        Certificate certificate = keyEntry.getCertificate();

        if (publicKey != null && privateKey != null && certificate != null) {
            isCertificateAvailable = true;
        }
        return  isCertificateAvailable;
    }

    public static void loadCertificates(KeyStore.PrivateKeyEntry keyEntry, Device device) throws CertificateEncodingException {

        PrivateKey privateKey = keyEntry.getPrivateKey();
        PublicKey publicKey = keyEntry.getCertificate().getPublicKey();
        Certificate certificate = keyEntry.getCertificate();

        byte[] certificateByteArray = Base64.encode(certificate.getEncoded(), Base64.DEFAULT);
        String convertedCertificate = new String(certificateByteArray);
        String encodedCertificate = CommonPreferences.CertificateConstants.BEGIN_CERTIFICATE + convertedCertificate + CommonPreferences.CertificateConstants.END_CERTIFICATE;
        device.setEncodedCertificate(encodedCertificate);
        device.setRsaPrivateKey(privateKey);
        device.setRsaPublicKey(publicKey);
    }
}
