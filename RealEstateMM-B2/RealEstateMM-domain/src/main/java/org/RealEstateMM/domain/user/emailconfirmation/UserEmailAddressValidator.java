package org.RealEstateMM.domain.user.emailconfirmation;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.emailsender.EmailSender;
import org.RealEstateMM.emailsender.email.EmailMessage;
import org.RealEstateMM.emailsender.email.EmailAddressConfirmationMessageGenerator;
import org.RealEstateMM.encoder.Encoder;

public class UserEmailAddressValidator {

	public static final String SEPARATOR = "WITH";

	private UserRepository userRepository;
	private EmailSender mailSender;
	private Encoder encoder;
	private EmailAddressConfirmationMessageGenerator messageGenerator;

	public UserEmailAddressValidator(UserRepository userRepo, EmailSender mailSender, Encoder encoder,
			EmailAddressConfirmationMessageGenerator messageGenerator) {
		this.userRepository = userRepo;
		this.mailSender = mailSender;
		this.encoder = encoder;
		this.messageGenerator = messageGenerator;
	}

	public void sendEmailConfirmationMessage(User user) {
		String infoToEncode = buildSecretToEncodeInConfirmationCode(user);
		String confirmationCode = encoder.encode(infoToEncode);
		EmailMessage email = messageGenerator.createEmailAddressConfirmationMessage(user.getEmailAddress(),
				confirmationCode);
		mailSender.sendEmail(email);
	}

	private String buildSecretToEncodeInConfirmationCode(User user) {
		return user.getPseudonym() + SEPARATOR + user.getEmailAddress();
	}

	public void confirmEmailAddress(String confirmationCode) {
		String pseudo = extractPseudonymFrom(confirmationCode);
		String emailAddress = extractEmailAddressFrom(confirmationCode);

		Optional<User> userOptional = userRepository.getUserWithPseudonym(pseudo);
		if (!userOptional.isPresent()) {
			throw new UserAssociatedToConfirmationCodeDoesNotExistException(pseudo);
		} else {
			User user = userOptional.get();
			if (user.hasEmailAddress(emailAddress)) {
				user.unlock();
				userRepository.persistUser(user);
			}
		}
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
