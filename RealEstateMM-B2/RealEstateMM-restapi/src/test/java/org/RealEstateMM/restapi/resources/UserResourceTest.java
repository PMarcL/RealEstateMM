package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.restapi.responses.LoginResponse;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.user.InvalidUserInformationsException;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.After;
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

	private UserServiceHandler userService;
	private SessionService sessionService;

	@Before
	public void setup() throws Throwable {
		userService = mock(UserServiceHandler.class);
		sessionService = mock(SessionService.class);

		given(userService.authenticate(A_PSEUDONYM, A_VALID_PASSWORD)).willReturn(A_USER_DTO);
		given(sessionService.open(A_USER_DTO)).willReturn(A_SESSION);
		given(sessionService.validate(A_VALID_TOKEN)).willReturn(A_PSEUDONYM);

		ServiceLocator.getInstance().registerService(UserServiceHandler.class, userService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);

		userConnectionResource = new UserResource();
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenAUserWhenRegisterUserThenShouldCallServiceLayer() throws Throwable {
		userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		verify(userService).createUser(A_USER_DTO);
	}

	@Test
	public void givenAValidUserWhenAUserIsRegisteredThenTheServerThenReturnResponseStatusOK() {
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenAUserWithInvalidInformationWhenCreateUserThenReturnsResponseBadRequest() throws Throwable {
		doThrow(InvalidUserInformationsException.class).when(userService).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenAUserWithValidInformationWhenUserWithSamePseudoAlreadyExistsThenReturnsResponseUnauthorized()
			throws Throwable {
		doThrow(ExistingUserException.class).when(userService).createUser(A_USER_DTO);
		StatusType statusType = userConnectionResource.signup(A_USER_DTO).getStatusInfo();
		assertEquals(Status.UNAUTHORIZED, statusType);
	}

	@Test
	public void givenAuthenticationFailedWhenLoginThenReturnResponseUnauthorizedError() throws Throwable {
		doThrow(AuthenticationFailedException.class).when(userService).authenticate(A_PSEUDONYM, AN_INVALID_PASSWORD);
		Response response = userConnectionResource.login(A_PSEUDONYM, AN_INVALID_PASSWORD);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenValidCredentialsWhenLoginThenReturnUserTypeAndToken() {
		Response response = userConnectionResource.login(A_PSEUDONYM, A_VALID_PASSWORD);
		LoginResponse expected = new LoginResponse(A_USER_DTO.getUserRole(), A_VALID_TOKEN);
		assertTrue(expected.equals((LoginResponse) response.getEntity()));
	}

	@Test
	public void givenAValidTokenWhenEditUserProfileThenGetUserPseudoWithSessionService() throws Throwable {
		userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO);
		verify(sessionService).validate(A_VALID_TOKEN);
	}

	@Test
	public void givenAnInvalidTokenWhenEditUserProfileThenReturnsUnauthorizedStatusCode() throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(A_VALID_TOKEN);
		Response response = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenAUserProfileOfANotExistingUserWhenEditUserProfileThenReturnsNoFoundStatusCode() throws Throwable {
		doThrow(UserNotFoundException.class).when(userService).updateUserProfile(A_PSEUDONYM, A_USER_DTO);
		Response response = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO);
		assertEquals(Status.NOT_FOUND, response.getStatusInfo());
	}

	@Test
	public void givenAUserProfileWhenEditUserProfileThenReturnsForbiddenStatusCodeIfAccessIsForbidden()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(userService).updateUserProfile(A_PSEUDONYM, A_USER_DTO);
		Response response = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenValidCredentialsWhenEditUserProfileThenReturnStatusOK() {
		StatusType statusType = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO).getStatusInfo();
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenValidCredentialsWhenEditUserProfileThenUsesServiceHandlerWithPseudoAndDTO() throws Throwable {
		userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO);
		verify(userService).updateUserProfile(A_PSEUDONYM, A_USER_DTO);
	}

	@Test
	public void givenInvalidCredentialsWhenEditUserProfileThenReturnResponseBadRequest() throws Throwable {
		doThrow(InvalidUserInformationsException.class).when(userService).updateUserProfile(A_PSEUDONYM, A_USER_DTO);
		StatusType statusType = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO).getStatusInfo();
		assertEquals(Status.BAD_REQUEST, statusType);
	}

	@Test
	public void givenErrorOccuredWhileEmailAddressConfirmationWhenEditUserProfileThenReturnResponseServerError()
			throws Throwable {
		doThrow(EmailAddressConfirmationException.class).when(userService).updateUserProfile(A_PSEUDONYM, A_USER_DTO);
		StatusType statusType = userConnectionResource.editUserProfile(A_VALID_TOKEN, A_USER_DTO).getStatusInfo();
		assertEquals(Status.INTERNAL_SERVER_ERROR, statusType);
	}

	@Test
	public void givenValidCredentialsWhenLoginThenCreateASession() {
		userConnectionResource.login(A_PSEUDONYM, A_VALID_PASSWORD);
		verify(sessionService, times(1)).open(A_USER_DTO);
	}

	@Test
	public void givenCredentialsWithInvalidInformationWhenloginThenReturnsResponseBadRequest() throws Throwable {
		doThrow(InvalidUserInformationsException.class).when(userService).authenticate(null, null);
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
			throws Throwable {
		String alreadyConfirmedCode = "alreadyConfirmedConfirmationCode";
		doThrow(EmailAddressConfirmationException.class).when(userService).confirmEmailAddress(alreadyConfirmedCode);

		Response response = userConnectionResource.confirmEmail(alreadyConfirmedCode);

		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void givenConnectedUserWhenGetUserProfileThenUsesSessionServiceToGetUserPseudo() throws Throwable {
		userConnectionResource.getUserProfile(A_VALID_TOKEN);
		verify(sessionService).validate(A_VALID_TOKEN);
	}

	@Test
	public void givenInvalidTokenWhenGetUserProfileThenReturnUnauthorizedStatusCode() throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(A_VALID_TOKEN);
		Response response = userConnectionResource.getUserProfile(A_VALID_TOKEN);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenExistingUserPseudonymWhenGetUserProfileThenReturnStatusOk() throws Throwable {
		given(userService.getUserProfile(A_PSEUDONYM)).willReturn(A_USER_DTO);
		Response response = userConnectionResource.getUserProfile(A_VALID_TOKEN);
		assertEquals(Status.OK, response.getStatusInfo());
	}

	@Test
	public void givenUserDoesNotExistWhenGetUserProfileThenReturnBadRequest() throws Throwable {
		given(userService.getUserProfile(anyString())).willThrow(new UserNotFoundException(UNEXISTING_PSEUDONYM));
		Response response = userConnectionResource.getUserProfile(A_VALID_TOKEN);
		assertEquals(Status.NOT_FOUND, response.getStatusInfo());
	}

	@Test
	public void givenExistingUserPseudonymWhenGetUserProfileThenResponseContainsUserProfileInfos() throws Throwable {
		given(userService.getUserProfile(A_PSEUDONYM)).willReturn(A_USER_DTO);
		Response response = userConnectionResource.getUserProfile(A_VALID_TOKEN);
		assertSame(A_USER_DTO, response.getEntity());
	}
}
