package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.restapi.resources.MessageRessource;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.message.dtos.MessageDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageRessourceTest {

	private static final String A_VALID_TOKEN = "aValidToken";
	private static final String A_PSEUDONYM = "SomeCoolPseudo";

	private MessageService messageService;

	private MessageRessource contactRequestResource;

	private SessionService sessionService;

	@Before
	public void setUp() throws Exception {
		messageService = mock(MessageService.class);
		sessionService = mock(SessionService.class);

		given(sessionService.validate(A_VALID_TOKEN)).willReturn(A_PSEUDONYM);

		ServiceLocator.getInstance().registerService(MessageService.class, messageService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);

		contactRequestResource = new MessageRessource();
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenNoTokenWhenContactSellerThenReturnBadRequestStatus() {
		HttpHeaders headers = aHeaderMockWithAuthorizationHeader(null);
		Response actual = contactRequestResource.contactSeller(headers, null);
		assertEquals(Status.BAD_REQUEST, actual.getStatusInfo());
	}

	@Test
	public void givenAnInvalidTokenWhenContactSellerThenReturnUnauthorizedStatus() throws InvalidSessionTokenException {
		String invalidToken = "invalidToken";
		given(sessionService.validate(invalidToken)).willThrow(new InvalidSessionTokenException());

		HttpHeaders headers = aHeaderMockWithAuthorizationHeader(invalidToken);
		Response actual = contactRequestResource.contactSeller(headers, null);

		assertEquals(Status.UNAUTHORIZED, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenContactSellerThenReturnsStatusOk() {
		HttpHeaders headers = aHeaderMockWithAuthorizationHeader(A_VALID_TOKEN);
		Response actual = contactRequestResource.contactSeller(headers, null);
		assertEquals(Status.OK, actual.getStatusInfo());
	}

	@Test
	public void givenAValidTokenWhenContactSellerThenCalledContactSellerOnServiceWithTheBuyerUsernameAndTheRequestInfo() {
		HttpHeaders headers = aHeaderMockWithAuthorizationHeader(A_VALID_TOKEN);
		MessageDTO message = new MessageDTO("Allo, I wanna, maybe, buy your beautiful house", "recipentUsername");
		contactRequestResource.contactSeller(headers, message);
		verify(messageService, times(1)).contactSeller(A_PSEUDONYM, message);
	}

	private HttpHeaders aHeaderMockWithAuthorizationHeader(String token) {
		HttpHeaders headers = mock(HttpHeaders.class);
		given(headers.getHeaderString("Authorization")).willReturn(token);
		return headers;
	}
}