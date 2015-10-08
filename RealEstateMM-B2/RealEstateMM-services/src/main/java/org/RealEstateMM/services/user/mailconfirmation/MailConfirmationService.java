package org.RealEstateMM.services.user.mailconfirmation;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.mail.MailSender;
import org.RealEstateMM.services.mail.email.Email;
import org.RealEstateMM.services.mail.email.EmailFactory;

public class MailConfirmationService {

	private MailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;
	private EmailFactory emailFactory;

	public MailConfirmationService() {
		mailSender = ServiceLocator.getInstance().getService(MailSender.class);
		emailFactory = new EmailFactory();
		// TODO emailConfirmationEncoder
	}

	public MailConfirmationService(MailSender mailSender, EmailConfirmationEncoder emailConfirmationEncoder,
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

}
