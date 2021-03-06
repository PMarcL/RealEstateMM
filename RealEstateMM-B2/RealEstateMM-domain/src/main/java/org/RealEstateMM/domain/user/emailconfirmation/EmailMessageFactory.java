package org.RealEstateMM.domain.user.emailconfirmation;

public class EmailMessageFactory {
	private final String BASE_URL;

	public EmailMessageFactory(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public EmailAddressConfirmationMessage createEmailAddressConfirmationMessage(String recipientEmailAddress,
			ConfirmationCode confirmationCode) {
		return new EmailAddressConfirmationMessage(recipientEmailAddress, confirmationCode, BASE_URL);
	}

}
