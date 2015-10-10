package org.RealEstateMM.emailsender.email;

public class EmailFactory {
	private final String BASE_URL;

	public EmailFactory(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public EmailAddressConfirmationEmail createEmailAddressConfirmationEmail(String recipientEmailAddress,
			String confirmationCode) {
		return new EmailAddressConfirmationEmail(recipientEmailAddress, confirmationCode, BASE_URL);
	}

}
