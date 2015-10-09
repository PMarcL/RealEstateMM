package org.RealEstateMM.services.user.mailconfirmation;

import java.nio.charset.Charset;
import java.util.Base64;

import org.RealEstateMM.domain.user.User;

public class Base64EmailConfirmationEncoder implements EmailConfirmationEncoder {

	private static final String SEPARATOR = "WITH";

	@Override
	public String getConfirmationCode(User user) {
		String stringToEncode = user.getPseudonym() + SEPARATOR + user.getEmailAddress();
		byte[] bytesToEncode = stringToEncode.getBytes(Charset.forName("UTF-8"));
		return Base64.getEncoder().encodeToString(bytesToEncode);
	}

	@Override
	public String extractPseudonymFromConfirmationCode(String confirmationCode) {
		byte[] decodeResult = Base64.getDecoder().decode(confirmationCode);
		String stringExtracted = new String(decodeResult);
		String pseudo = stringExtracted.split("WITH")[0];
		return pseudo;
	}

}
