package org.RealEstateMM.domain.user.emailconfirmation;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.emailsender.EmailSender;
import org.RealEstateMM.emailsender.email.Email;
import org.RealEstateMM.emailsender.email.EmailFactory;
import org.RealEstateMM.encoder.Encoder;

public class EmailConfirmer {

	public static final String SEPARATOR = "WITH";

	private UserRepository userRepository;
	private EmailSender mailSender;
	private Encoder encoder;
	private EmailFactory emailFactory;

	public EmailConfirmer(UserRepository userRepo, EmailSender mailSender, Encoder emailConfirmationEncoder,
			EmailFactory emailFactory) {
		this.userRepository = userRepo;
		this.mailSender = mailSender;
		this.encoder = emailConfirmationEncoder;
		this.emailFactory = emailFactory;
	}

	public void sendEmailConfirmation(User user) {
		String infoToEncode = buildSecretToEncodeInConfirmationCode(user);
		String confirmationCode = encoder.encode(infoToEncode);
		String recipientEmailAddress = user.getEmailAddress();
		Email email = emailFactory.createEmailAddressConfirmationEmail(recipientEmailAddress, confirmationCode);
		mailSender.sendEmail(email);
	}

	public void confirmEmailAddress(String confirmationCode) {
		String pseudo = extractPseudonymFrom(confirmationCode);
		String emailAddress = extractEmailAddressFrom(confirmationCode);

		Optional<User> userOptional = userRepository.getUserWithPseudonym(pseudo);
		if (!userOptional.isPresent()) {
			throw new UserAssociatedToConfirmationCodeDoesNotExistException(pseudo);
		} else {
			userOptional.get().confirmEmailAddress(emailAddress);
		}
	}

	private String buildSecretToEncodeInConfirmationCode(User user) {
		return user.getPseudonym() + SEPARATOR + user.getEmailAddress();
	}

	private String extractPseudonymFrom(String confirmationCode) {
		String[] decodedInfo = encoder.decode(confirmationCode).split(SEPARATOR);
		checkFormat(decodedInfo);
		return decodedInfo[0];
	}

	private String extractEmailAddressFrom(String confirmationCode) {
		String[] decodedInfo = encoder.decode(confirmationCode).split(SEPARATOR);
		checkFormat(decodedInfo);
		return decodedInfo[1];
	}

	private void checkFormat(String[] decodedInfo) {
		if (decodedInfo.length != 2) {
			throw new InvalidEmailConfirmationCodeException();
		}

	}

}
