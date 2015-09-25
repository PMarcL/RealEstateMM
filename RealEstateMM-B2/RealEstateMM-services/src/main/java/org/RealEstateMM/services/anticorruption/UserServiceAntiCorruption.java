package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class UserServiceAntiCorruption {

	private UserInformationsValidator informationsValidator;
	private UserService userService;

	public UserServiceAntiCorruption(UserService service, UserInformationsValidator validator) {
		userService = service;
		informationsValidator = validator;
	}

	public void createUser(UserDTO userDTO) {
		validateUserDTO(userDTO);
		userService.createUser(userDTO);
	}

	public boolean userExists(String pseudonym) {
		if (!informationsValidator.nameIsValid(pseudonym)) {
			throw new InvalidUserInformationsException("Pseudonym");
		}
		return userService.userExists(pseudonym);
	}

	private void validateUserDTO(UserDTO userDTO) {
		if (!informationsValidator.nameIsValid(userDTO.getFirstName())) {
			throw new InvalidUserInformationsException("FirstName");
		}
		if (!informationsValidator.nameIsValid(userDTO.getLastName())) {
			throw new InvalidUserInformationsException("LastName");
		}
		if (!informationsValidator.emailIsValid(userDTO.getEmail())) {
			throw new InvalidUserInformationsException("Email");
		}
		if (!informationsValidator.phoneNumberIsValid(userDTO.getPhoneNumber())) {
			throw new InvalidUserInformationsException("PhoneNumber");
		}
	}

}
