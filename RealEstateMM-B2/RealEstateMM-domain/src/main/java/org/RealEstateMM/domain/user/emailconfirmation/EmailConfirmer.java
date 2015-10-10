package org.RealEstateMM.domain.user.emailconfirmation;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.emailsender.EmailSender;
import org.RealEstateMM.emailsender.email.Email;
import org.RealEstateMM.emailsender.email.EmailFactory;

public class EmailConfirmer {

	private EmailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;
	private EmailFactory emailFactory;

	public EmailConfirmer(EmailSender mailSender, EmailConfirmationEncoder emailConfirmationEncoder,
			EmailFactory emailFactory) {
		this.mailSender = mailSender;
		this.emailConfirmationEncoder = emailConfirmationEncoder;
		this.emailFactory = emailFactory;
	}

	public void sendEmailConfirmation(User user) {
		String confirmationCode = emailConfirmationEncoder.getConfirmationCode(user);
		String recipientEmailAddress = user.getEmailAddress();
		Email email = emailFactory.createEmailAddressConfirmationEmail(recipientEmailAddress, confirmationCode);
		mailSender.sendEmail(email);
	}

	public String extractPseudonymFrom(String confirmationCode) {
		return emailConfirmationEncoder.extractPseudonymFromConfirmationCode(confirmationCode);
	}

}
