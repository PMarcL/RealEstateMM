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
	private final String INVALID_PSEUDO = "";
	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();

	private UserInformationsValidator validator;
	private UserService service;

	private UserServiceAntiCorruption userServiceAC;

	@Before
	public void initialisation() {
		validator = mock(UserInformationsValidator.class);
		service = mock(UserService.class);
		userServiceAC = new UserServiceAntiCorruption(service, validator);
		allFieldsAreValid();
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyFirstName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).nameIsValid(DefaultUserValue.FIRST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksForEmptyLastName() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).nameIsValid(DefaultUserValue.LAST_NAME);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksEmailValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).emailIsValid(DefaultUserValue.EMAIL);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenChecksPhoneNumberValidity() {
		userServiceAC.createUser(A_USER_DTO);
		verify(validator).phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER);
	}

	@Test
	public void givenNewUserInformationsWhenRegisterNewUserThenRegisterUserWithService() {
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
		when(validator.nameIsValid(VALID_PSEUDO)).thenReturn(true);
		userServiceAC.userExists(VALID_PSEUDO);
		verify(service).userExists(VALID_PSEUDO);
	}

	@Test
	public void givenAPseudonymWhenCheckingUserExistanceThenChecksPseudonymValidity() {
		when(validator.nameIsValid(VALID_PSEUDO)).thenReturn(true);
		userServiceAC.userExists(VALID_PSEUDO);
		verify(validator).nameIsValid(VALID_PSEUDO);
	}

	@Test(expected = InvalidUserInformationsException.class)
	public void givenAPseudonymWhenPseudonymIsInvalidThenThrowException() {
		when(validator.nameIsValid(INVALID_PSEUDO)).thenReturn(false);
		userServiceAC.userExists(INVALID_PSEUDO);
	}

	private void allFieldsAreValid() {
		when(validator.nameIsValid(DefaultUserValue.FIRST_NAME)).thenReturn(true);
		when(validator.nameIsValid(DefaultUserValue.LAST_NAME)).thenReturn(true);
		when(validator.phoneNumberIsValid(DefaultUserValue.PHONE_NUMBER)).thenReturn(true);
		when(validator.emailIsValid(DefaultUserValue.EMAIL)).thenReturn(true);

	}
}
