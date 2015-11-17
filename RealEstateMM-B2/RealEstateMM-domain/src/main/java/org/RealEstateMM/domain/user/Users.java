package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.emailconfirmation.ImpossibleToConfirmEmailAddressException;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.domain.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.domain.user.exceptions.UnconfirmedEmailException;

public class Users {

	private UserRepository userRepository;
	private UserEmailAddressValidator emailValidator;

	public Users(UserRepository userRepository, UserEmailAddressValidator emailValidator) {
		this.userRepository = userRepository;
		this.emailValidator = emailValidator;
	}

	public void addUser(User newUser) {
		userRepository.addUser(newUser);
		emailValidator.sendEmailConfirmationMessage(newUser.getUserInformations());
	}

	public User authenticate(String pseudonym, String password)
			throws UnconfirmedEmailException, InvalidPasswordException {
		User user = userRepository.getUserWithPseudonym(pseudonym);
		user.authenticate(password);
		return user;
	}

	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		try {
			emailValidator.confirmEmailAddress(confirmationCode, userRepository);
		} catch (InvalidEmailConfirmationCodeException e) {
			throw new ImpossibleToConfirmEmailAddressException(e);
		}
	}

	public User getUser(String pseudonym) {
		return userRepository.getUserWithPseudonym(pseudonym);
	}

	public void updateUserProfile(UserInformations userInformations) {
		User user = userRepository.getUserWithPseudonym(userInformations.pseudonym);
		boolean emailChanged = !(user.hasEmailAddress(userInformations.emailAddress));
		user.updateUserInformations(userInformations);

		if (emailChanged) {
			user.lock();
			emailValidator.sendEmailConfirmationMessage(userInformations);
		}

		userRepository.replaceUser(user);
	}

}
