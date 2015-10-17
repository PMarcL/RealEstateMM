package org.RealEstateMM.domain.user.emailconfirmation;

import org.RealEstateMM.domain.encoder.Encoder;

public class ConfirmationCodeFactory {

	private Encoder encoder;

	public ConfirmationCodeFactory(Encoder encoder) {
		this.encoder = encoder;
	}

	public ConfirmationCode createConfirmationCode(String pseudonym, String emailAddress) {
		return new ConfirmationCode(encoder, pseudonym, emailAddress);
	}

	public ConfirmationCode createConfirmationCode(String confirmationCodeValue) {
		return new ConfirmationCode(encoder, confirmationCodeValue);
	}

}
