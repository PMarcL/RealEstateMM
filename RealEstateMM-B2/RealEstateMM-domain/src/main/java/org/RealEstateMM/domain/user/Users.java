package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.emailsender.EmailException;

public class Users {

	private UserRepository userRepository;
	private UserInformationsValidator emailValidator;

	public Users(UserRepository userRepository, UserInformationsValidator emailValidator) {
		this.userRepository = userRepository;
		this.emailValidator = emailValidator;
	}

	public void addUser(User newUser) throws ExistingUserException, EmailAddressConfirmationException {
		userRepository.addUser(newUser);
		askEmailAddressConfirmation(newUser.getUserInformations());
	}

	private void askEmailAddressConfirmation(UserInformations userInfos) throws EmailAddressConfirmationException {
		try {
			emailValidator.sendValidationRequest(userInfos);
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
			emailValidator.confirmUserInformation(confirmationCode, userRepository);
		} catch (InvalidConfirmationCodeException e) {
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
