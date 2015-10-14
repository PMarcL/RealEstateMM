package org.RealEstateMM.emailsender.email;

import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCode;

public class EmailAddressConfirmationMessage extends EmailMessage {

	private static final String SUBJECT = "House match email confirmation";
	private static final String RESOURCE_PATH = "user/emailConfirmation/";

	public EmailAddressConfirmationMessage(String recipientEmailAddress, ConfirmationCode confirmationCode,
			String baseUrl) {
		super(recipientEmailAddress, SUBJECT, createBody(createUrl(baseUrl, confirmationCode)));
	}

	private static String createUrl(String baseUrl, ConfirmationCode confirmationCode) {
		// TODO test the url is well built
		return baseUrl + RESOURCE_PATH + confirmationCode.toString();
	}

	private static String createBody(String emailConfirmationUrl) {
		String message = "Thank you for creating an account on HouseMatch.";
		message += " You'll see that it is the best decision you've ever made !";
		message += "\nTo confirm your email address click on the link below:\n";
		message += emailConfirmationUrl;
		return message;
	}

}
