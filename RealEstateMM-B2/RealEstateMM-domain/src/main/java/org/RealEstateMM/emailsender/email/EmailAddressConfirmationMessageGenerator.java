package org.RealEstateMM.emailsender.email;

public class EmailAddressConfirmationMessageGenerator {
	private final String BASE_URL;

	public EmailAddressConfirmationMessageGenerator(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public EmailAddressConfirmationMessage createEmailAddressConfirmationMessage(String recipientEmailAddress,
			String confirmationCode) {
		return new EmailAddressConfirmationMessage(recipientEmailAddress, confirmationCode, BASE_URL);
	}

}
