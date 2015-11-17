package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.emailsender.EmailException;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;

public class Users {

	private UserRepository userRepository;
	private UserEmailAddressValidator emailValidator;

	public Users(UserRepository userRepository, UserEmailAddressValidator emailValidator) {
		this.userRepository = userRepository;
		this.emailValidator = emailValidator;
	}

	public void addUser(User newUser) throws ExistingUserException, EmailAddressConfirmationException {
		userRepository.addUser(newUser);
		askEmailAddressConfirmation(newUser.getUserInformations());
	}

	private void askEmailAddressConfirmation(UserInformations userInfos) throws EmailAddressConfirmationException {
		try {
			emailValidator.sendEmailConfirmationMessage(userInfos);
		} catch (EmailException e) {
			throw new EmailAddressConfirmationException(e);
		}
	}

	public User authenticate(String pseudonym, String password) throws AuthenticationFailedException {
		try {
			User user = userRepository.getUserWithPseudonym(pseudonym);
			user.authenticate(password);
			return user;
		} catch (UserNotFoundException | UnconfirmedEmailException | InvalidPasswordException e) {
			throw new AuthenticationFailedException(e);
		}
	}

	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException {
		try {
			emailValidator.confirmEmailAddress(confirmationCode, userRepository);
		} catch (InvalidEmailConfirmationCodeException e) {
			throw new EmailAddressConfirmationException(e);
		}
	}

	public User getUser(String pseudonym) throws UserNotFoundException {
		return userRepository.getUserWithPseudonym(pseudonym);
	}

	public void updateUserProfile(UserInformations userInformations)
			throws UserNotFoundException, EmailAddressConfirmationException {
		User user = userRepository.getUserWithPseudonym(userInformations.pseudonym);
		boolean emailChanged = !(user.hasEmailAddress(userInformations.emailAddress));
		user.updateUserInformations(userInformations);

		if (emailChanged) {
			user.lock();
			askEmailAddressConfirmation(userInformations);
		}

		userRepository.replaceUser(user);
	}

}
