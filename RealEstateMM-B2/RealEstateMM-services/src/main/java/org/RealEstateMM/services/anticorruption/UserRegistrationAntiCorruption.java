package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.dto.UserDTO;

public class UserRegistrationAntiCorruption {

	private UserInformationsValidator informationsValidator;
	private UserService registrationService;

	public UserRegistrationAntiCorruption(UserService service, UserInformationsValidator validator) {
		registrationService = service;
		informationsValidator = validator;
	}

	public void createUser(UserDTO userInfos) {
		validateUserInformation(userInfos);
		registrationService.createUser(userInfos);
	}

	private void validateUserInformation(UserDTO userDTO) {
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
