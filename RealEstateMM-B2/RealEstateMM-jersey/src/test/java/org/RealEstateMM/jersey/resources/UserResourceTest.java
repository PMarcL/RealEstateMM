package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.repository.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.services.InvalidPasswordException;
import org.RealEstateMM.services.UserDoesNotExistException;
import org.RealEstateMM.services.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {

	private static final String A_PSEUDONYM = "Joe90";
	private static final String A_VALID_PASSWORD = "right";

	private static final String AN_INVALID_PASSWORD = "wrong";
	private static final String UNEXISTING_PSEUDONYM = "IDoNotExist";

	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();

	private UserResource userConnectionResource;

	private UserServiceAntiCorruption userServiceAC;
	private SessionService sessionService;

	@Before
	public void setup() throws Exception {
		userServiceAC = mock(UserServiceAntiCorruption.class);
		sessionService = mock(SessionService.class);

		given(userServiceAC.findUserType(A_PSEUDONYM, A_VALID_PASSWORD)).willReturn(A_USER_DTO);

		userConnectionResource = new UserResource(userServiceAC, sessionService);
	}

	@Test
	public void givenAUserWhenRegisterUserThenShouldCallServiceLayer() {
		userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		verify(userServiceAC).createUser(A_USER_DTO);
	}

	@Test
	public void givenAValidUserWhenAUserIsRegisteredThenTheServerShouldReturnResponseStatusOK() {
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenAUserWithInvalidInformationWhenCreateUserThenReturnsResponseBadRequest() {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenAUserWithValidInformationWhenUserWithSamePseudoAlreadyExistsThenReturnsResponseBadRequest() {
		doThrow(UserWithPseudonymAlreadyStoredException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.registerUser(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenInvalidPasswordWhenLoginThenReturnResponseUnauthorizedError() throws Exception {
		doThrow(InvalidPasswordException.class).when(userServiceAC).findUserType(A_PSEUDONYM, AN_INVALID_PASSWORD);
		Response response = userConnectionResource.logInUser(A_PSEUDONYM, AN_INVALID_PASSWORD);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenInvalidPseudonymWhenLoginThenReturnResponseUnauthorizedError() throws Exception {
		doThrow(UserDoesNotExistException.class).when(userServiceAC).findUserType(UNEXISTING_PSEUDONYM,
				AN_INVALID_PASSWORD);
		Response response = userConnectionResource.logInUser(UNEXISTING_PSEUDONYM, AN_INVALID_PASSWORD);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenValidCredentialsWhenLoginThenReturnUserType() throws Exception {
		Response response = userConnectionResource.logInUser(A_PSEUDONYM, A_VALID_PASSWORD);

		String actualJson = (String) response.getEntity();
		String expectedJson = "{\"userType\":\"" + A_USER_DTO.getUserType() + "\"}";
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void givenValidCredentialsWhenLoginThenCreateASession() {
		userConnectionResource.logInUser(A_PSEUDONYM, A_VALID_PASSWORD);
		verify(sessionService, times(1)).open(A_USER_DTO);
	}

	@Test
	public void givenCredentialsWIthInvalidInformationWhenloginThenReturnsResponseBadRequest() throws Exception {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).findUserType(null, null);
		Response response = userConnectionResource.logInUser(null, null);
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}
}
