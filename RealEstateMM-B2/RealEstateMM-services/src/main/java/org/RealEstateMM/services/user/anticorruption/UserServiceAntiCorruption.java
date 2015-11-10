package org.RealEstateMM.services.user.anticorruption;

import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.user.ImpossibleToConfirmEmailAddressException;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UnconfirmedEmailException;

public class UserServiceAntiCorruption implements UserServiceHandler {

	private UserInformationsValidator informationsValidator;
	private UserService userService;

	public UserServiceAntiCorruption(UserService service, UserInformationsValidator validator) {
		userService = service;
		informationsValidator = validator;
	}

	@Override
	public void createUser(UserDTO userDTO) {
		validateUserDTO(userDTO);
		userService.createUser(userDTO);
	}

	@Override
	public void updateUserProfile(UserDTO userDTO) {
		validateUserDTO(userDTO);
		userService.updateUserProfile(userDTO);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password) throws InvalidPasswordException,
			UserNotFoundException, UnconfirmedEmailException {
		if (!informationsValidator.stringIsValid(pseudonym)) {
			throw new InvalidUserInformationsException("Pseudonym");
		} else if (!informationsValidator.stringIsValid(password)) {
			throw new InvalidUserInformationsException("Password");
		}
		return userService.authenticate(pseudonym, password);
	}

	private void validateUserDTO(UserDTO userDTO) {
		if (!informationsValidator.nameIsValid(userDTO.getFirstName())) {
			throw new InvalidUserInformationsException("FirstName");
		}
		if (!informationsValidator.nameIsValid(userDTO.getLastName())) {
			throw new InvalidUserInformationsException("LastName");
		}
		if (!informationsValidator.emailIsValid(userDTO.getEmailAddress())) {
			throw new InvalidUserInformationsException("Email");
		}
		if (!informationsValidator.phoneNumberIsValid(userDTO.getPhoneNumber())) {
			throw new InvalidUserInformationsException("PhoneNumber");
		}
		if (!informationsValidator.userTypeIsValid(userDTO.getUserRole())) {
			throw new InvalidUserInformationsException("User type");
		}
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		userService.confirmEmailAddress(confirmationCode);
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) {
		return userService.getUserProfile(pseudonym);
	}
}
