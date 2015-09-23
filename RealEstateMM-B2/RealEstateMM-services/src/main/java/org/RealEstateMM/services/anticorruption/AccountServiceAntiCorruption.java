package org.RealEstateMM.services.anticorruption;

import org.RealEstateMM.services.AccountService;
import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class AccountServiceAntiCorruption {

	private UserInformationsValidator informationsValidator;
	private AccountService accountService;

	public AccountServiceAntiCorruption(AccountService service, UserInformationsValidator validator) {
		accountService = service;
		informationsValidator = validator;
	}

	public void createAccount(AccountDTO accountDTO) {
		validateUserDTO(accountDTO.getOwner());
		accountService.createAccount(accountDTO);
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
