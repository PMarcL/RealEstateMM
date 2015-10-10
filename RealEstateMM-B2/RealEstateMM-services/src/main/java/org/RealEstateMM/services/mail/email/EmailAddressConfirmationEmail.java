package org.RealEstateMM.services.mail.email;

public class EmailAddressConfirmationEmail extends Email {

	private static final String SUBJECT = "House match email confirmation";

	public EmailAddressConfirmationEmail(String recipientEmailAddress, String confirmationCode) {
		super(recipientEmailAddress, SUBJECT, createBody(confirmationCode));
	}

	// TODO BaseUrl
	private static String createBody(String confirmationCode) {
		String baseUrl = "banana.com/";
		String message = "Thank you for creating an account on HouseMatch.";
		message += " You'll see that it is the best decision you've ever made !";
		message += "\nTo confirm your email address click on the link below:";
		message += baseUrl + confirmationCode;
		return message;
	}

}
