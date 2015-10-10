package org.RealEstateMM.services.mail.email;

public class EmailFactory {

	public EmailAddressConfirmationEmail createEmailAddressConfirmationEmail(String recipientEmailAddress,
			String confirmationCode) {
		return new EmailAddressConfirmationEmail(recipientEmailAddress, confirmationCode);
	}

}
