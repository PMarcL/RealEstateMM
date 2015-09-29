package org.RealEstateMM.authentication.session;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class SessionServiceTest {

	private final User A_USER = new UserBuilder().build();
	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();

	private SessionService sessionService;
	private SessionRepository sessionRepository;

	@Before
	public void setUp() {
		sessionRepository = mock(SessionRepository.class);
		UserAssembler userAssembler = mock(UserAssembler.class);

		given(userAssembler.fromDTO(A_USER_DTO)).willReturn(A_USER);

		sessionService = new SessionService(sessionRepository, userAssembler);
	}

	@Test
	public void whenOpenSessionThenAddOrOverwriteANewSessionForThatUserInTheSessionRepository() {

		sessionService.openSessionWithRole(A_USER_DTO);

		ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
		verify(sessionRepository, times(1)).saveOrOverwriteSession(captor.capture());
		assertEquals(A_USER.getPseudonym(), captor.getValue().pseudonym);

		verifyNoMoreInteractions(sessionRepository);
	}

	@Test
	public void whenOpenSessionThenReturnTheCreatedSession() {
		Session actual = sessionService.openSessionWithRole(A_USER_DTO);

		assertEquals(A_USER.getPseudonym(), actual.pseudonym);
		assertNotNull(actual.token);
	}

}
