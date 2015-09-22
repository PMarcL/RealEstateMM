package org.RealEstateMM.services.anticorruption;

import static org.mockito.Mockito.*;

import org.RealEstateMM.services.UserRegistrationService;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;

public class UserRegistrationAntiCorruptionTest {

	private final String EMAIL = "john90@hotmail.com";
	private final String PHONENUMBER = "(819) 456-4839";
	private final String FIRSTNAME = "John";
	private final String LASTNAME = "Doe";

	private UserRegistrationAntiCorruption userRegistrationAC;

	private UserInformationsValidator validator;
	private UserDTO userInfos;
	private UserRegistrationService service;

	@Before
	public void initialisation() {
		validator = mock(UserInformationsValidator.class);
		service = mock(UserRegistrationService.class);
		userRegistrationAC = new UserRegistrationAntiCorruption(service, validator);
		allFieldsAreValid();
		createUserDTO();
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyFirstName() {
		userRegistrationAC.register(userInfos);
		verify(validator).nameIsValid(FIRSTNAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyLastName() {
		userRegistrationAC.register(userInfos);
		verify(validator).nameIsValid(LASTNAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksEmailValidity() {
		userRegistrationAC.register(userInfos);
		verify(validator).emailIsValid(EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksPhoneNumberValidity() {
		userRegistrationAC.register(userInfos);
		verify(validator).phoneNumberIsValid(PHONENUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenRegisterUserWithService() {
		userRegistrationAC.register(userInfos);
		verify(service).register(userInfos);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() {
		when(validator.emailIsValid(EMAIL)).thenReturn(false);
		userRegistrationAC.register(userInfos);
	}

	private void createUserDTO() {
		userInfos = new UserDTO();
		userInfos.setEmail(EMAIL);
		userInfos.setPhoneNumber(PHONENUMBER);
		userInfos.setFirstName(FIRSTNAME);
		userInfos.setLastName(LASTNAME);
	}

	private void allFieldsAreValid() {
		when(validator.nameIsValid(FIRSTNAME)).thenReturn(true);
		when(validator.nameIsValid(LASTNAME)).thenReturn(true);
		when(validator.phoneNumberIsValid(PHONENUMBER)).thenReturn(true);
		when(validator.emailIsValid(EMAIL)).thenReturn(true);

	}
}
