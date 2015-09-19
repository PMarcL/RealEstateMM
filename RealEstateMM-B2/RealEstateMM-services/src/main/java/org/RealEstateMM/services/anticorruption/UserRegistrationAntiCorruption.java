package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.UserRegistrationService;
import org.RealEstateMM.services.dto.UserDTO;

public class UserRegistrationAntiCorruption {

	private UserInformationsValidator informationsValidator;
	private UserRegistrationService registrationService;

	public UserRegistrationAntiCorruption(UserRegistrationService service, UserInformationsValidator validator) {
		registrationService = service;
		informationsValidator = validator;
	}

	public void register(UserDTO userDTO) {
		validateUserInformation(userDTO);
		registrationService.register(userDTO);
	}

	private void validateUserInformation(UserDTO userDTO) {
		if (!informationsValidator.nameIsValid(userDTO.firstName)) {
			throw new InvalidUserInformationsException("FirstName");
		}
		if (!informationsValidator.nameIsValid(userDTO.lastName)) {
			throw new InvalidUserInformationsException("LastName");
		}
		if (!informationsValidator.emailIsValid(userDTO.email)) {
			throw new InvalidUserInformationsException("Email");
		}
		if (!informationsValidator.phoneNumberIsValid(userDTO.phoneNumber)) {
			throw new InvalidUserInformationsException("PhoneNumber");
		}
	}

}
