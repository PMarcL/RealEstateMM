package org.RealEstateMM.services.user;

import java.util.Optional;

import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UnconfirmedEmailException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;

public class UserService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserEmailAddressValidator emailAddressValidator;

	public UserService(UserRepository userRepository, UserAssembler accountAssembler,
			UserEmailAddressValidator emailConfirmer) {
		this.userRepository = userRepository;
		this.userAssembler = accountAssembler;
		this.emailAddressValidator = emailConfirmer;
	}

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserAssembler();
		emailAddressValidator = ServiceLocator.getInstance().getService(UserEmailAddressValidator.class);
	}

	public void createUser(UserDTO userDTO) throws CouldNotSendMailException {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		emailAddressValidator.sendEmailConfirmationMessage(newUser.getUserInformations());
	}

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

	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		try {
			emailAddressValidator.confirmEmailAddress(confirmationCode, userRepository);
		} catch (InvalidEmailConfirmationCodeException e) {
			throw new ImpossibleToConfirmEmailAddressException(e);
		}
	}

	public void updateUserProfile(UserDTO userProfile) throws UserDoesNotExistException {
		User user = findUserWithPseudonym(userProfile.getPseudonym());
		user.updateUserInformations(createUserInfosFromDTO(userProfile));
		userRepository.replaceUser(user);
	}

	private UserInformations createUserInfosFromDTO(UserDTO userProfile) {
		return new UserInformations(userProfile.getPseudonym(), userProfile.getPassword(), userProfile.getFirstName(),
				userProfile.getLastName(), userProfile.getEmail(), userProfile.getPhoneNumber());
	}

}
