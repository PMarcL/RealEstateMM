package org.RealEstateMM.domain.encoder;

import java.nio.charset.Charset;
import java.util.Base64;

public class Base64Encoder implements Encoder {

	@Override
	public String encode(String stringToEncode) {
		byte[] bytesToEncode = stringToEncode.getBytes(Charset.forName("UTF-8"));
		return Base64.getEncoder().encodeToString(bytesToEncode);
	}

	@Override
	public String decode(String confirmationCode) {
		byte[] decodeResult = Base64.getDecoder().decode(confirmationCode);
		return new String(decodeResult);
	}

}
