package org.RealEstateMM.domain.user.emailconfirmation;

import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.email.EmailMessage;
import org.RealEstateMM.domain.emailsender.email.EmailMessageFactory;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;

public class UserEmailAddressValidator {

	private ConfirmationCodeFactory confirmCodeFactory;
	private EmailMessageFactory messageFactory;
	private EmailSender emailSender;

	public UserEmailAddressValidator(ConfirmationCodeFactory confirmCodeFactory, EmailMessageFactory messageFactory,
			EmailSender emailSender) {
		this.confirmCodeFactory = confirmCodeFactory;
		this.messageFactory = messageFactory;
		this.emailSender = emailSender;
	}

	public void sendEmailConfirmationMessage(UserInformations userInfos) {
		EmailMessage message = createEmail(userInfos);
		emailSender.sendEmail(message);
	}

	private EmailMessage createEmail(UserInformations userInfos) {
		ConfirmationCode code = confirmCodeFactory.createConfirmationCode(userInfos.pseudonym, userInfos.emailAddress);
		return messageFactory.createEmailAddressConfirmationMessage(userInfos.emailAddress, code);
	}

	public void confirmEmailAddress(String confirmationCodeValue, UserRepository users) {
		ConfirmationCode confirmationCode = confirmCodeFactory.createConfirmationCode(confirmationCodeValue);
		User user;
		try {
			user = users.getUserWithPseudonym(confirmationCode.getPseudonym());
		} catch (UserNotFoundException e) {
			return;
		}

		if (user.hasEmailAddress(confirmationCode.getEmailAddress())) {
			user.unlock();
			users.replaceUser(user);
		}
	}

}
