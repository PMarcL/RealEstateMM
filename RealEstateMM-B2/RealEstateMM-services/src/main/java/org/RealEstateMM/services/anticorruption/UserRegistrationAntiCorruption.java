package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.UserRegistrationService;
import org.RealEstateMM.services.dto.UserInformations;

public class UserRegistrationAntiCorruption {

	private UserInformationsValidator informationsValidator;
	private UserRegistrationService registrationService;

	public UserRegistrationAntiCorruption(UserRegistrationService service, UserInformationsValidator validator) {
		registrationService = service;
		informationsValidator = validator;
	}

	public void register(UserInformations userInfos) {
		validateUserInformation(userInfos);
		registrationService.register(userInfos);
	}

	private void validateUserInformation(UserInformations userDTO) {
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
