package org.RealEstateMM.services.anticorruption;

import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserServiceAntiCorruptionTest {

	private final String VALID_PSEUDO = "pseudo34";
	private final String VALID_PASSWORD = "pw1234";
	private final String INVALID_STRING = "";
	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();

	private UserInformationsValidator validator;
	private UserService service;

	private UserServiceAntiCorruption userServiceAC;

	@Before
	public void setup() {
		validator = mock(UserInformationsValidator.class);
		service = mock(UserService.class);
		userServiceAC = new UserServiceAntiCorruption(service, validator);
		allFieldsAreValid();
		setupInvalidFields();
	}

	private void setupInvalidFields() {
		when(validator.stringIsValid(INVALID_STRING)).thenReturn(false);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyFirstName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).stringIsValid(UserBuilder.DEFAULT_FIRST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyLastName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).stringIsValid(UserBuilder.DEFAULT_LAST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksEmailValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).emailIsValid(UserBuilder.DEFAULT_EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksPhoneNumberValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).phoneNumberIsValid(UserBuilder.DEFAULT_PHONE_NUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksUserTypeValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).userTypeIsValid(UserBuilder.DEFAULT_USER_TYPE_DESC);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenRegisterUserWithService() {
		userServiceAC.createUser(A_USER_DTO);
		verify(service).create(A_USER_DTO);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() {
		when(validator.emailIsValid(UserBuilder.DEFAULT_EMAIL)).thenReturn(false);
		userServiceAC.createUser(A_USER_DTO);
	}

	@Test
	public void givenAPseudonymWhenPseudonymIsValidThenCallsServiceToCheckUserExistance() throws Exception {
		userServiceAC.findUserType(VALID_PSEUDO, VALID_PASSWORD);
		verify(service).authenticate(VALID_PSEUDO, VALID_PASSWORD);
	}

	@Test
	public void givenAPseudonymWhenCheckingUserExistanceThenChecksPseudonymValidity() throws Exception {
		userServiceAC.findUserType(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).stringIsValid(VALID_PSEUDO);
	}

	@Test
	public void givenAPasswordWhenCheckingUserExistanceThenChecksPasswordValidity() throws Exception {
		userServiceAC.findUserType(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).stringIsValid(VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAPseudonymWhenPseudonymIsInvalidThenThrowException() throws Exception {
		userServiceAC.findUserType(INVALID_STRING, VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAnInvalidPasswordWhenCheckingUserExistanceThenThrowException() throws Exception {
		userServiceAC.findUserType(INVALID_STRING, INVALID_STRING);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAValidPseudoAndInvalidePasswordWhenCheckinUserExistanceThenThrowException() throws Exception {
		userServiceAC.findUserType(VALID_PSEUDO, INVALID_STRING);
	}

	private void allFieldsAreValid() {
		when(validator.stringIsValid(UserBuilder.DEFAULT_FIRST_NAME)).thenReturn(true);
		when(validator.stringIsValid(UserBuilder.DEFAULT_LAST_NAME)).thenReturn(true);
		when(validator.phoneNumberIsValid(UserBuilder.DEFAULT_PHONE_NUMBER)).thenReturn(true);
		when(validator.emailIsValid(UserBuilder.DEFAULT_EMAIL)).thenReturn(true);
		when(validator.stringIsValid(VALID_PASSWORD)).thenReturn(true);
		when(validator.stringIsValid(VALID_PSEUDO)).thenReturn(true);
		when(validator.userTypeIsValid(UserBuilder.DEFAULT_USER_TYPE_DESC)).thenReturn(true);
	}
}
