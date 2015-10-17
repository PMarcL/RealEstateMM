package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.UserWithPseudonymAlreadyStoredException;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.user.ImpossibleToConfirmEmailAddressException;
import org.RealEstateMM.services.user.anticorruption.InvalidUserInformationsException;
import org.RealEstateMM.services.user.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;
import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {

	private static final String A_PSEUDONYM = "Joe90";
	private static final String A_VALID_PASSWORD = "right";
	private static final String AN_INVALID_PASSWORD = "wrong";
	private static final String UNEXISTING_PSEUDONYM = "IDoNotExist";
	private static final String A_VALID_TOKEN = "valid123";
	private static final String A_VALID_CONFIRMATION_CODE = "aValidConfirmationCode";

	private static final UserDTO A_USER_DTO = new UserDTOBuilder().build();
	private static final Session A_SESSION = new Session(A_USER_DTO.getPseudonym(), A_VALID_TOKEN);

	private UserResource userConnectionResource;

	private UserServiceAntiCorruption userServiceAC;
	private SessionService sessionService;

	@Before
	public void setup() throws Exception {
		userServiceAC = mock(UserServiceAntiCorruption.class);
		sessionService = mock(SessionService.class);

		given(userServiceAC.login(A_PSEUDONYM, A_VALID_PASSWORD)).willReturn(A_USER_DTO);
		given(sessionService.open(A_USER_DTO)).willReturn(A_SESSION);

		userConnectionResource = new UserResource(userServiceAC, sessionService);
	}

	@Test
	public void givenAUserWhenRegisterUserThenShouldCallServiceLayer() {
		userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		verify(userServiceAC).createUser(A_USER_DTO);
	}

	@Test
	public void givenAValidUserWhenAUserIsRegisteredThenTheServerShouldReturnResponseStatusOK() {
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenAUserWithInvalidInformationWhenCreateUserThenReturnsResponseBadRequest() {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenAUserWithValidInformationWhenUserWithSamePseudoAlreadyExistsThenReturnsResponseBadRequest() {
		doThrow(UserWithPseudonymAlreadyStoredException.class).when(userServiceAC).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenInvalidPasswordWhenLoginThenReturnResponseUnauthorizedError() throws Exception {
		doThrow(InvalidPasswordException.class).when(userServiceAC).login(A_PSEUDONYM, AN_INVALID_PASSWORD);
		Response response = userConnectionResource.login(A_PSEUDONYM, AN_INVALID_PASSWORD);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenInvalidPseudonymWhenLoginThenReturnResponseUnauthorizedError() throws Exception {
		doThrow(UserDoesNotExistException.class).when(userServiceAC).login(UNEXISTING_PSEUDONYM, AN_INVALID_PASSWORD);
		Response response = userConnectionResource.login(UNEXISTING_PSEUDONYM, AN_INVALID_PASSWORD);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenValidCredentialsWhenLoginThenReturnUserTypeAndToken() throws Exception {
		Response response = userConnectionResource.login(A_PSEUDONYM, A_VALID_PASSWORD);
		LoginResponse expected = new LoginResponse(A_USER_DTO.getUserType(), A_VALID_TOKEN);
		assertTrue(expected.equals((LoginResponse) response.getEntity()));
	}

	@Test
	public void givenValidCredentialsWhenEditUserProfileThenReturnStatusOK() throws Exception {
		StatusType statusType = userConnectionResource.editUserProfile(A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenInvalidCredentialsWhenEditUserProfileThenReturnResponseBadRequest() throws Exception {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).updateUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.editUserProfile(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenValidCredentialsWhenLoginThenCreateASession() {
		userConnectionResource.login(A_PSEUDONYM, A_VALID_PASSWORD);
		verify(sessionService, times(1)).open(A_USER_DTO);
	}

	@Test
	public void givenCredentialsWithInvalidInformationWhenloginThenReturnsResponseBadRequest() throws Exception {
		doThrow(InvalidUserInformationsException.class).when(userServiceAC).login(null, null);
		Response response = userConnectionResource.login(null, null);
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenLogoutThenCloseSession() {
		userConnectionResource.logout(A_VALID_TOKEN);
		verify(sessionService, times(1)).close(A_VALID_TOKEN);
	}

	@Test
	public void givenATokenWhenLogoutThenReturnStatusOK() {
		Response response = userConnectionResource.logout(A_VALID_TOKEN);
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void givenNoTokenWhenLogoutThenReturnStatusBadRequest() {
		String noToken = null;
		Response response = userConnectionResource.logout(noToken);
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void givenAValidConfirmationCodeWhenConfirmEmailAddressThenReturnStatusOK() {
		Response response = userConnectionResource.confirmEmail(A_VALID_CONFIRMATION_CODE);
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void givenAnAlreadyConfirmedConfirmationCodeWhenConfirmEmailAddressThenReturnStatusBadRequest()
			throws ImpossibleToConfirmEmailAddressException {
		String alreadyConfirmedCode = "alreadyConfirmedConfirmationCode";
		doThrow(ImpossibleToConfirmEmailAddressException.class).when(userServiceAC).confirmEmailAddress(
				alreadyConfirmedCode);

		Response response = userConnectionResource.confirmEmail(alreadyConfirmedCode);

		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void givenExistingUserPseudonymWhenGetUserProfileThenReturnStatusOk() {
		given(userServiceAC.getUserProfile(A_PSEUDONYM)).willReturn(A_USER_DTO);
		Response response = userConnectionResource.getUserProfile(A_PSEUDONYM);
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void givenUserDoesNotExistWhenGetUserProfileThenReturnBadRequest() {
		given(userServiceAC.getUserProfile(anyString())).willThrow(new UserDoesNotExistException());
		Response response = userConnectionResource.getUserProfile(A_PSEUDONYM);
		assertEquals(Status.NOT_FOUND, response.getStatusInfo());
	}

	@Test
	public void givenExistingUserPseudonymWhenGetUserProfileThenResponseContainsUserProfileInfos() {
		given(userServiceAC.getUserProfile(A_PSEUDONYM)).willReturn(A_USER_DTO);
		Response response = userConnectionResource.getUserProfile(A_PSEUDONYM);
		assertSame(A_USER_DTO, response.getEntity());
	}
}
