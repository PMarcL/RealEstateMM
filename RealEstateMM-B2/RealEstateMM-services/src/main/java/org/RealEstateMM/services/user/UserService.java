package org.RealEstateMM.services.user;

import java.util.Optional;

import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UnconfirmedEmailException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;

public class UserService implements UserServiceHandler {

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserEmailAddressValidator emailAddressValidator;

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = ServiceLocator.getInstance().getService(UserAssembler.class);
		emailAddressValidator = ServiceLocator.getInstance().getService(UserEmailAddressValidator.class);
	}

	@Override
	public void createUser(UserDTO userDTO) throws CouldNotSendMailException {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		emailAddressValidator.sendEmailConfirmationMessage(newUser.getUserInformations());
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password)
			throws InvalidPasswordException, UserDoesNotExistException, UnconfirmedEmailException {

		User user = findUserWithPseudonym(pseudonym);
		if (user.isLocked()) {
			throw new UnconfirmedEmailException();
		}
		if (!user.hasPassword(password)) {
			throw new InvalidPasswordException();
		}
		return userAssembler.toDTO(user);
	}

	private User findUserWithPseudonym(String pseudonym) throws UserDoesNotExistException {
		Optional<User> user = userRepository.getUserWithPseudonym(pseudonym);
		if (!(user.isPresent())) {
			throw new UserDoesNotExistException();
		}
		return user.get();
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		try {
			emailAddressValidator.confirmEmailAddress(confirmationCode, userRepository);
		} catch (InvalidEmailConfirmationCodeException e) {
			throw new ImpossibleToConfirmEmailAddressException(e);
		}
	}

	@Override
	public void updateUserProfile(UserDTO userProfile) throws UserDoesNotExistException {
		User user = findUserWithPseudonym(userProfile.getPseudonym());
		boolean emailChanged = !(user.hasEmailAddress(userProfile.getEmailAddress()));
		user.updateUserInformations(createUserInfosFromDTO(userProfile));
		if (emailChanged) {
			user.lock();
			emailAddressValidator.sendEmailConfirmationMessage(user.getUserInformations());
		}
		userRepository.replaceUser(user);
	}

	private UserInformations createUserInfosFromDTO(UserDTO userProfile) {
		return new UserInformations(userProfile.getPseudonym(), userProfile.getPassword(), userProfile.getFirstName(),
				userProfile.getLastName(), userProfile.getEmailAddress(), userProfile.getPhoneNumber());
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) throws UserDoesNotExistException {
		User user = findUserWithPseudonym(pseudonym);
		return userAssembler.toDTO(user);
	}

}
