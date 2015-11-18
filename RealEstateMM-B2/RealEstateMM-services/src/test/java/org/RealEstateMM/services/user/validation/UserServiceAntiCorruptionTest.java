package org.RealEstateMM.services.user.validation;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.dtos.UserDTO;
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
		given(validator.isStringValid(INVALID_STRING)).willReturn(false);
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyFirstName() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).nameIsValid(A_USER_DTO.getFirstName());
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksForEmptyLastName() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).nameIsValid(A_USER_DTO.getLastName());
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksEmailValidity() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).isEmailValid(A_USER_DTO.getEmailAddress());
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksPhoneNumberValidity() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).isPhoneNumberValid(A_USER_DTO.getPhoneNumber());
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenChecksUserTypeValidity() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).userTypeIsValid(A_USER_DTO.getUserRole());
	}

	@Test
	public void givenNewUserInformationsWhenCreateNewUserThenRegisterUserWithService() throws Throwable {
		userServiceAC.createUser(A_USER_DTO);
		verify(service).createUser(A_USER_DTO);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenNewUserInformationsWhenUserInformationIsNotValidThenThrowException() throws Throwable {
		given(validator.isEmailValid(A_USER_DTO.getEmailAddress())).willReturn(false);
		userServiceAC.createUser(A_USER_DTO);
	}

	@Test
	public void givenUpdateInformationWhenUpdateInformationThenRegisterUserUpdatedWithService() throws Throwable {
		userServiceAC.updateUserProfile(VALID_PSEUDO, A_USER_DTO);
		verify(service).updateUserProfile(VALID_PSEUDO, A_USER_DTO);
	}

	@Test
	public void givenAPseudonymWhenPseudonymIsValidThenCallsServiceToCheckUserExistance() throws Exception {
		userServiceAC.authenticate(VALID_PSEUDO, VALID_PASSWORD);
		verify(service).authenticate(VALID_PSEUDO, VALID_PASSWORD);
	}

	@Test
	public void givenAPseudonymWhenCheckingUserExistanceThenChecksPseudonymValidity() throws Exception {
		userServiceAC.authenticate(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).isStringValid(VALID_PSEUDO);
	}

	@Test
	public void givenAPasswordWhenCheckingUserExistanceThenChecksPasswordValidity() throws Exception {
		userServiceAC.authenticate(VALID_PSEUDO, VALID_PASSWORD);
		verify(validator).isStringValid(VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAPseudonymWhenPseudonymIsInvalidThenThrowException() throws Exception {
		userServiceAC.authenticate(INVALID_STRING, VALID_PASSWORD);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAnInvalidPasswordWhenCheckingUserExistanceThenThrowException() throws Exception {
		userServiceAC.authenticate(INVALID_STRING, INVALID_STRING);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAValidPseudoAndInvalidPasswordWhenCheckinUserExistanceThenThrowException() throws Exception {
		userServiceAC.authenticate(VALID_PSEUDO, INVALID_STRING);
	}

	@Test
	public void givenPseudonymWhenGetUserProfileThenGetUserProfileFromUserService() throws Throwable {
		userServiceAC.getUserProfile(VALID_PSEUDO);
		verify(service).getUserProfile(VALID_PSEUDO);
	}

	private void allFieldsAreValid() {
		given(validator.nameIsValid(A_USER_DTO.getFirstName())).willReturn(true);
		given(validator.nameIsValid(A_USER_DTO.getLastName())).willReturn(true);
		given(validator.isPhoneNumberValid(A_USER_DTO.getPhoneNumber())).willReturn(true);
		given(validator.isEmailValid(A_USER_DTO.getEmailAddress())).willReturn(true);
		given(validator.isStringValid(VALID_PASSWORD)).willReturn(true);
		given(validator.isStringValid(VALID_PSEUDO)).willReturn(true);
		given(validator.userTypeIsValid(A_USER_DTO.getUserRole())).willReturn(true);
	}
}
