package org.RealEstateMM.services.user.mailconfirmation;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.mail.MailSender;
import org.RealEstateMM.services.mail.email.Email;
import org.RealEstateMM.services.mail.email.EmailFactory;

public class EmailConfirmationService {

	private MailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;
	private EmailFactory emailFactory;

	public EmailConfirmationService() {
		mailSender = ServiceLocator.getInstance().getService(MailSender.class);
		emailConfirmationEncoder = ServiceLocator.getInstance().getService(EmailConfirmationEncoder.class);
		emailFactory = new EmailFactory();
	}

	public EmailConfirmationService(MailSender mailSender, EmailConfirmationEncoder emailConfirmationEncoder,
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

	public String getConfirmingUserPseudonym(String confirmationCode) {
		return emailConfirmationEncoder.extractPseudonymFromConfirmationCode(confirmationCode);
	}

}
