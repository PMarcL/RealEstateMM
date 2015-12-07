package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.message.dtos.MessageDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageResourceTest {

	private static final String AN_INVALID_TOKEN = "anInvalidToken";
	private static final String A_VALID_TOKEN = "aValidToken";
	private static final String A_PSEUDONYM = "SomeCoolPseudo";
	private static final MessageDTO A_MESSAGE_DTO = new MessageDTO("allo le monde", "senderUsername",
			"recipentUsername");

	private MessageService messageService;

	private MessageResource messageResource;

	private SessionService sessionService;

	@Before
	public void setUp() throws Exception {
		messageService = mock(MessageService.class);
		sessionService = mock(SessionService.class);

		given(sessionService.validate(A_VALID_TOKEN)).willReturn(A_PSEUDONYM);
		given(sessionService.validate(AN_INVALID_TOKEN)).willThrow(new InvalidSessionTokenException());

		ServiceLocator.getInstance().registerService(MessageService.class, messageService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);

		messageResource = new MessageResource();
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenNoTokenWhenContactSellerThenReturnBadRequestStatus() {
		Response actual = messageResource.contactSeller(null, null);
		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAnInvalidTokenWhenContactSellerThenReturnUnauthorizedStatus() throws InvalidSessionTokenException {
		Response actual = messageResource.contactSeller(AN_INVALID_TOKEN, null);

		assertEquals(Status.UNAUTHORIZED, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenContactSellerThenReturnsStatusOk() {
		Response actual = messageResource.contactSeller(A_VALID_TOKEN, null);
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenContactSellerThenCalledContactSellerOnServiceWithTheBuyerUsernameAndTheRequestInfo()
			throws UserNotFoundException, UserIsNotASellerException {

		messageResource.contactSeller(A_VALID_TOKEN, A_MESSAGE_DTO);
		verify(messageService, times(1)).contactSeller(A_PSEUDONYM, A_MESSAGE_DTO);
	}

	@Test
	public void givenAUserIsNotASellerWhenContactSellerThenReturnABadRequestStatus() throws UserNotFoundException,
			UserIsNotASellerException {

		doThrow(new UserIsNotASellerException(null)).when(messageService).contactSeller(A_PSEUDONYM, A_MESSAGE_DTO);

		Response actual = messageResource.contactSeller(A_VALID_TOKEN, A_MESSAGE_DTO);

		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAUserNotFoundWhenContactSellerThenReturnABadRequestStatus() throws UserNotFoundException,
			UserIsNotASellerException {

		doThrow(new UserNotFoundException(null)).when(messageService).contactSeller(A_PSEUDONYM, A_MESSAGE_DTO);

		Response actual = messageResource.contactSeller(A_VALID_TOKEN, A_MESSAGE_DTO);

		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAnInvalidTokenWhenGetMessagesThenReturnsUnauthorizedStatus() {
		Response actual = messageResource.getUnreadMessages(AN_INVALID_TOKEN);

		assertEquals(Status.UNAUTHORIZED, actual.getStatusInfo());

	}

	@Test
	public void givenNoTokenWhenGetMessagesThenReturnsBadRequestStatus() {
		Response actual = messageResource.getUnreadMessages(null);
		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenGetMessageThenReturnsStatusOk() {
		Response actual = messageResource.getUnreadMessages(A_VALID_TOKEN);
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenGetMessagesThenReturnsUsersMessages() {
		List<MessageDTO> messages = new LinkedList<MessageDTO>();
		messages.add(A_MESSAGE_DTO);
		messages.add(A_MESSAGE_DTO);
		given(messageService.getUserMessages(A_PSEUDONYM)).willReturn(messages);

		Response actual = messageResource.getUnreadMessages(A_VALID_TOKEN);

		assertEquals(messages, actual.getEntity());
	}

	@Test
	public void givenNoTokenWhenReadMessageThenReturnBadRequestStatus() {
		Response actual = messageResource.contactSeller(null, null);
		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAnInvalidTokenWhenReadMessageThenReturnUnauthorizedStatus() {
		Response actual = messageResource.contactSeller(AN_INVALID_TOKEN, null);

		assertEquals(Status.UNAUTHORIZED, actual.getStatusInfo());
	}

}