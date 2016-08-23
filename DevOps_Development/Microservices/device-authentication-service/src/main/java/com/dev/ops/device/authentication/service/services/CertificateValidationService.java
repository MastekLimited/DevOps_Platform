package com.dev.ops.device.authentication.service.services;

import java.math.BigDecimal;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.StringTokenizer;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.dev.ops.common.constants.CommonConstants;
import com.dev.ops.device.authentication.service.constants.DeviceAuthenticationConstants;
import com.dev.ops.device.authentication.service.domain.DeviceAuthentication;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;

@Component
public class CertificateValidationService {

	private final JcaX509CertificateConverter certificateConverter;
	private final JcaSimpleSignerInfoVerifierBuilder certificateBuilder;
	private final BASE64Encoder base64Encoder;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(CertificateValidationService.class);

	public CertificateValidationService() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		this.certificateConverter = new JcaX509CertificateConverter();
		this.certificateConverter.setProvider("BC");

		this.certificateBuilder = new JcaSimpleSignerInfoVerifierBuilder();
		this.certificateBuilder.setProvider("BC");

		this.base64Encoder = new BASE64Encoder();
	}

	public DeviceAuthentication getDeviceAuthentication(final String signedMessage) throws CMSException, CertificateException, OperatorCreationException, DefaultWrappedException {
		DeviceAuthentication deviceAuthentication = null;
		final CMSSignedData signedData = new CMSSignedData(Base64.decode(signedMessage.getBytes()));

		if(this.isCertificateValid(signedData)) {
			deviceAuthentication = this.getDecodedSignedContent(signedData);
		} else {
			throw new DefaultWrappedException(DeviceAuthenticationConstants.ExceptionCodes.CERTIFICATE_VALIDATION_EXCEPTION);
		}
		return deviceAuthentication;
	}

	private boolean isCertificateValid(final CMSSignedData signedData) throws CertificateException, CMSException, OperatorCreationException {

		boolean authVarifired = false;
		final SignerInformationStore signers = signedData.getSignerInfos();
		final Store store = signedData.getCertificates();

		for(final Object signerObject : signers.getSigners()) {
			final SignerInformation signer = (SignerInformation) signerObject;
			for(final Object certificateHolderObject : store.getMatches(signer.getSID())) {
				final X509CertificateHolder certificateHolder = (X509CertificateHolder) certificateHolderObject;
				final X509Certificate certificate = this.certificateConverter.getCertificate(certificateHolder);

				if(signer.verify(this.certificateBuilder.build(certificate))) {
					DIAGNOSTIC_LOGGER.debug("certifate has been verified");
					authVarifired = true;
				}
				//added break because we need to check only first value.
				break;
			}
		}
		return authVarifired;
	}

	private DeviceAuthentication getDecodedSignedContent(final CMSSignedData signedData) {

		final byte[] contents = (byte[]) signedData.getSignedContent().getContent();

		final String encodedContent = this.base64Encoder.encode(contents);

		DIAGNOSTIC_LOGGER.debug("content information: " + encodedContent);

		final String decodedContent = new String(Base64.decode(encodedContent.getBytes()));

		DIAGNOSTIC_LOGGER.debug("decoded content information: " + decodedContent);

		final StringTokenizer token = new StringTokenizer(decodedContent, CommonConstants.Separators.DOLLAR_SEPARATOR);

		return new DeviceAuthentication(new BigDecimal(token.nextToken()), token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken());
	}
}