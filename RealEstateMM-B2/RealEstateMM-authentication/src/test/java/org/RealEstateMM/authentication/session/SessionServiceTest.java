package org.RealEstateMM.authentication.session;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class SessionServiceTest {
	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();

	private SessionService sessionService;
	private SessionRepository sessionRepository;

	@Before
	public void setUp() {
		sessionRepository = mock(SessionRepository.class);

		sessionService = new SessionService(sessionRepository);
	}

	@Test
	public void whenOpenSessionThenAddOrOverwriteANewSessionForThatUserInTheSessionRepository() {
		sessionService.open(A_USER_DTO);

		ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
		verify(sessionRepository).addOrOverwriteSession(captor.capture());
		assertEquals(A_USER_DTO.getPseudonym(), captor.getValue().pseudonym);
	}

	@Test
	public void whenOpenSessionThenReturnTheCreatedSession() {
		Session actual = sessionService.open(A_USER_DTO);

		assertEquals(A_USER_DTO.getPseudonym(), actual.pseudonym);
		assertNotNull(actual.token);
	}

	@Test
	public void givenATokenwhenCloseThenRemoveTheSessionFromRepository() {
		String anyToken = "aToken";
		sessionService.close(anyToken);
		verify(sessionRepository).removeSesionWithToken(anyToken);
	}

	@Test
	public void givenAValidTokenWhenValidateThenReturnTheUsername() throws InvalidSessionTokenException {
		String aPseudonym = "Joe";
		String aValidToken = "valid19b1290";
		Session aSession = new Session(aPseudonym, aValidToken);
		given(sessionRepository.getByToken(aValidToken)).willReturn(Optional.of(aSession));

		String actual = sessionService.validate(aValidToken);

		assertEquals(aPseudonym, actual);
	}

	@Test(expected = InvalidSessionTokenException.class)
	public void givenAnInvalidTokenWhenValidateThenThrowATokenInvalidException() throws InvalidSessionTokenException {
		String anInvalidToken = "invalid1289n";
		given(sessionRepository.getByToken(anInvalidToken)).willReturn(Optional.empty());
		sessionService.validate(anInvalidToken);
	}

}
