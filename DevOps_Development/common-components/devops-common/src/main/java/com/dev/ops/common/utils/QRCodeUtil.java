package com.dev.ops.common.utils;

import java.io.IOException;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QRCodeUtil {

	public static byte[] getQRCodeImage(final String encodingString) throws IOException {
		byte[] imgBytes = new byte[0];
		imgBytes = QRCode.from(encodingString).to(ImageType.JPG).stream().toByteArray();
		return imgBytes;
	}
}
