package org.RealEstateMM.emailsender.email;

public class EmailAddressConfirmationMessage extends EmailMessage {

	private static final String SUBJECT = "House match email confirmation";
	private static final String RESOURCE_PATH = "user/emailConfirmation/";

	public EmailAddressConfirmationMessage(String recipientEmailAddress, String confirmationCode, String baseUrl) {
		super(recipientEmailAddress, SUBJECT, createBody(confirmationCode, baseUrl));
	}

	private static String createBody(String confirmationCode, String baseUrl) {
		String message = "Thank you for creating an account on HouseMatch.";
		message += " You'll see that it is the best decision you've ever made !";
		message += "\nTo confirm your email address click on the link below:";
		message += baseUrl + RESOURCE_PATH + confirmationCode;
		return message;
	}

}
