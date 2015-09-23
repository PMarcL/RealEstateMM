package org.RealEstateMM.services.anticorruption;

import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.helpers.DefaultUserValue;
import org.RealEstateMM.services.AccountService;
import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.AccountDTOBuilder;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceAntiCorruptionTest {

	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();
	private final AccountDTO AN_ACCOUNT_DTO = new AccountDTOBuilder().withOwner(A_USER_DTO).build();

	private UserInformationsValidator validator;
	private AccountService service;

	private AccountServiceAntiCorruption accountServiceAC;

	@Before
	public void initialisation() {
		validator = mock(UserInformationsValidator.class);
		service = mock(AccountService.class);
		accountServiceAC = new AccountServiceAntiCorruption(service, validator);
		allFieldsAreValid();
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyFirstName() {
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
		verify(validator).nameIsValid(DefaultUserValue.FIRST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyLastName() {
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
		verify(validator).nameIsValid(DefaultUserValue.LAST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksEmailValidity() {
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
		verify(validator).emailIsValid(DefaultUserValue.EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksPhoneNumberValidity() {
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
		verify(validator).phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenRegisterUserWithService() {
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
		verify(service).createAccount(AN_ACCOUNT_DTO);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() {
		when(validator.emailIsValid(DefaultUserValue.EMAIL)).thenReturn(false);
		accountServiceAC.createAccount(AN_ACCOUNT_DTO);
	}

	private void allFieldsAreValid() {
		when(validator.nameIsValid(DefaultUserValue.FIRST_NAME)).thenReturn(true);
		when(validator.nameIsValid(DefaultUserValue.LAST_NAME)).thenReturn(true);
		when(validator.phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER)).thenReturn(true);
		when(validator.emailIsValid(DefaultUserValue.EMAIL)).thenReturn(true);

	}
}
