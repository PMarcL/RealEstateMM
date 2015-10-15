package org.RealEstateMM.domain.user.emailconfirmation;

import org.RealEstateMM.domain.encoder.Encoder;

public class ConfirmationCode {
	private final String SEPARATOR = ";";

	private String pseudonym;
	private String emailAddress;
	private String confirmationCodeValue;

	public ConfirmationCode(Encoder encoder, String pseudonym, String emailAddress) {
		this.pseudonym = pseudonym;
		this.emailAddress = emailAddress;
		this.confirmationCodeValue = encoder.encode(assembleStringToEncode());
	}

	public ConfirmationCode(Encoder encoder, String confirmationCodeValue) {
		this.confirmationCodeValue = confirmationCodeValue;
		decodeConfirmationCodeValue(encoder, confirmationCodeValue);
	}

	private void decodeConfirmationCodeValue(Encoder encoder, String confirmationCodeValue) {
		String decodedCode = encoder.decode(confirmationCodeValue);
		String[] codeParts = decodedCode.split(SEPARATOR);
		if (codeParts.length != 2) {
			throw new InvalidEmailConfirmationCodeException();
		}

		this.pseudonym = codeParts[0];
		this.emailAddress = codeParts[1];
	}

	private String assembleStringToEncode() {
		return pseudonym + SEPARATOR + emailAddress;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	@Override
	public String toString() {
		return confirmationCodeValue;
	}

}
