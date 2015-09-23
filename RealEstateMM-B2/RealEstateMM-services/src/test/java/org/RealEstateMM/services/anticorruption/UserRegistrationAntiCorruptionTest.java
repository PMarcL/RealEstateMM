package org.RealEstateMM.services.anticorruption;

import static org.mockito.Mockito.*;

import org.RealEstateMM.services.UserService;
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
	private UserService service;

	@Before
	public void initialisation() {
		validator = mock(UserInformationsValidator.class);
		service = mock(UserService.class);
		userRegistrationAC = new UserRegistrationAntiCorruption(service, validator);
		allFieldsAreValid();
		createUserDTO();
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyFirstName() {
		userRegistrationAC.createUser(userInfos);
		verify(validator).nameIsValid(FIRSTNAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyLastName() {
		userRegistrationAC.createUser(userInfos);
		verify(validator).nameIsValid(LASTNAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksEmailValidity() {
		userRegistrationAC.createUser(userInfos);
		verify(validator).emailIsValid(EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksPhoneNumberValidity() {
		userRegistrationAC.createUser(userInfos);
		verify(validator).phoneNumberIsValid(PHONENUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenRegisterUserWithService() {
		userRegistrationAC.createUser(userInfos);
		verify(service).createUser(userInfos);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() {
		when(validator.emailIsValid(EMAIL)).thenReturn(false);
		userRegistrationAC.createUser(userInfos);
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
