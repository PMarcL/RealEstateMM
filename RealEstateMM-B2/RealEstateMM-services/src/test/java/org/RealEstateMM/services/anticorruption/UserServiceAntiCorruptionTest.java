package org.RealEstateMM.services.anticorruption;

import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.helpers.DefaultUserValue;
import org.RealEstateMM.services.UserService;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserServiceAntiCorruptionTest {

	private final String VALID_PSEUDO = "pseudo34";
	private final String VALID_PASSWORD = "pw1234";
	private final String INVALID_PSEUDO = "";
	private final String INVALID_PASSWORD = "";
	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();

	private UserInformationsValidator validator;
	private UserService service;

	private UserServiceAntiCorruption userServiceAC;

	@Before
	public void setup() {
		validator = mock(UserInformationsValidator.class);
		service = mock(UserService.class);
		userServiceAC = new UserServiceAntiCorruption(service, validator);
		allFieldsAreValid();
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyFirstName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).stringIsValid(DefaultUserValue.FIRST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyLastName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).stringIsValid(DefaultUserValue.LAST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksEmailValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).emailIsValid(DefaultUserValue.EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksPhoneNumberValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksUserTypeValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).userTypeIsValid(DefaultUserValue.USER_TYPE_DESC);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenRegisterUserWithService() {
		userServiceAC.createUser(A_USER_DTO);
		verify(service).createUser(A_USER_DTO);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() {
		when(validator.emailIsValid(DefaultUserValue.EMAIL)).thenReturn(false);
		userServiceAC.createUser(A_USER_DTO);
	}

	@Test
	public void givenAPseudonymWhenPseudonymIsValidThenCallsServiceToCheckUserExistance() {
		userServiceAC.userExists(VALID_PSEUDO, VALID_PASSWORD);
		verify(service).userExists(VALID_PSEUDO, VALID_PASSWORD);
	}

	@Test
	public void givenAPseudonymWhenCheckingUserExistanceThenChecksPseudonymValidity() {
		userServiceAC.userExists(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).stringIsValid(VALID_PSEUDO);
	}

	@Test
	public void givenAPasswordWhenCheckingUserExistanceThenChecksPasswordValidity() {
		userServiceAC.userExists(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).stringIsValid(VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAPseudonymWhenPseudonymIsInvalidThenThrowException() {
		when(validator.stringIsValid(INVALID_PSEUDO)).thenReturn(false);
		userServiceAC.userExists(INVALID_PSEUDO, VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAnInvalidPasswordWhenCheckingUserExistanceThenThrowException() {
		when(validator.stringIsValid(INVALID_PASSWORD)).thenReturn(false);
		userServiceAC.userExists(INVALID_PSEUDO, INVALID_PASSWORD);
	}

	private void allFieldsAreValid() {
		when(validator.stringIsValid(DefaultUserValue.FIRST_NAME)).thenReturn(true);
		when(validator.stringIsValid(DefaultUserValue.LAST_NAME)).thenReturn(true);
		when(validator.phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER)).thenReturn(true);
		when(validator.emailIsValid(DefaultUserValue.EMAIL)).thenReturn(true);
		when(validator.stringIsValid(VALID_PASSWORD)).thenReturn(true);
		when(validator.stringIsValid(VALID_PSEUDO)).thenReturn(true);
		when(validator.userTypeIsValid(DefaultUserValue.USER_TYPE_DESC)).thenReturn(true);
	}
}
