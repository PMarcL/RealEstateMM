package org.RealEstateMM.services.user.validation;

import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.user.ForbiddenAccessException;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.dtos.UserDTO;

public class UserServiceAntiCorruption implements UserServiceHandler {

	private UserInformationsValidator informationsValidator;
	private UserServiceHandler userService;

	public UserServiceAntiCorruption(UserServiceHandler service, UserInformationsValidator validator) {
		userService = service;
		informationsValidator = validator;
	}

	@Override
	public void createUser(UserDTO userDTO)
			throws ExistingUserException, EmailAddressConfirmationException, InvalidUserInformationsException {
		validateUserDTO(userDTO);
		userService.createUser(userDTO);
	}

	@Override
	public void updateUserProfile(String pseudo, UserDTO userDTO) throws ForbiddenAccessException,
			UserNotFoundException, EmailAddressConfirmationException, InvalidUserInformationsException {
		validateUserDTO(userDTO);
		userService.updateUserProfile(pseudo, userDTO);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password)
			throws AuthenticationFailedException, InvalidUserInformationsException {
		if (!informationsValidator.isStringValid(pseudonym)) {
			throw new InvalidUserInformationsException("Pseudonym");
		} else if (!informationsValidator.isStringValid(password)) {
			throw new InvalidUserInformationsException("Password");
		}
		return userService.authenticate(pseudonym, password);
	}

	private void validateUserDTO(UserDTO userDTO) throws InvalidUserInformationsException {
		if (!informationsValidator.nameIsValid(userDTO.getFirstName())) {
			throw new InvalidUserInformationsException("FirstName");
		}
		if (!informationsValidator.nameIsValid(userDTO.getLastName())) {
			throw new InvalidUserInformationsException("LastName");
		}
		if (!informationsValidator.isEmailValid(userDTO.getEmailAddress())) {
			throw new InvalidUserInformationsException("Email");
		}
		if (!informationsValidator.isPhoneNumberValid(userDTO.getPhoneNumber())) {
			throw new InvalidUserInformationsException("PhoneNumber");
		}
		if (!informationsValidator.userTypeIsValid(userDTO.getUserRole())) {
			throw new InvalidUserInformationsException("User type");
		}
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException {
		userService.confirmEmailAddress(confirmationCode);
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException {
		return userService.getUserProfile(pseudonym);
	}
}
