package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.*;

import org.RealEstateMM.domain.builders.DefaultUserBuilder;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class SessionServiceTest {

	private SessionService sessionService;

	@Before
	public void setUp() {
	}

	@Test
	public void givenAUserNotConnectedWhenCreateSessionThenCreateANewSessionForThatUserOnlyOnce() {
		User USER_NOT_CONNECTED = new DefaultUserBuilder().withPseudonym("Jack").build();
		SessionRepository sessionRepo = mock(SessionRepository.class);
		sessionService = new SessionService(sessionRepo);

		sessionService.createSession(USER_NOT_CONNECTED);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(sessionRepo, times(1)).addSession(captor.capture(), anyString());
		assertEquals(USER_NOT_CONNECTED, captor.getValue());

		verifyNoMoreInteractions(sessionRepo);
	}
}
