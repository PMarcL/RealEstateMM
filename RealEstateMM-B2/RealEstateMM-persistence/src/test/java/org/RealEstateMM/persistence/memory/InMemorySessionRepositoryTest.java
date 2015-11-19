package org.RealEstateMM.persistence.memory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.authentication.session.Session;
import org.junit.Before;
import org.junit.Test;

public class InMemorySessionRepositoryTest {

	private static final String UNEXISTING_TOKEN = "unexisting token";

	private Session session1 = new Session("pseudo1", "token1");
	private Session session2 = new Session("pseudo2", "token2");
	private Session session3 = new Session("pseudo3", "token3");

	private Map<String, Session> map;
	private InMemorySessionRepository repository;

	@Before
	public void setUp() {
		map = spy(new HashMap<String, Session>());
		repository = new InMemorySessionRepository(map);
	}

	@Test
	public void givenASessionWhenSaveOrOverwriteThenAddItToItsData() {
		Session newSession = new Session("pseudo", "token");

		repository.addOrOverwriteSession(newSession);

		// Map.put already overwrite if needed !
		verify(map, times(1)).put(newSession.pseudonym, newSession);
		verifyNoMoreInteractions(map);
	}

	@Test
	public void givenAnUnexistingTokenWhenRemoveSessionWithTokenThenDoNotDoAnything() {
		fillMapWithSessions();
		repository.removeSesionWithToken(UNEXISTING_TOKEN);
		verify(map, times(0)).remove(session2.pseudonym);

	}

	@Test
	public void givenAValidTokenWhenRemoveSessionWithTokenThenRemoveSession() {
		fillMapWithSessions();
		repository.removeSesionWithToken(session2.token);
		verify(map, times(1)).remove(session2.pseudonym);
	}

	@Test
	public void givenAValidTokenWhenGetWithTokenThenReturnAnOptionalOfTheSessionWithTheToken() {
		fillMapWithSessions();
		Optional<Session> actual = repository.getByToken(session2.token);
		assertEquals(session2, actual.get());
	}

	@Test
	public void givenAnInexistingTokenWhenGetWithTokenThenReturnAnOptionalEmpty() {
		fillMapWithSessions();
		Optional<Session> actual = repository.getByToken(UNEXISTING_TOKEN);
		assertEquals(Optional.empty(), actual);
	}

	private void fillMapWithSessions() {
		map.put(session1.pseudonym, session1);
		map.put(session2.pseudonym, session2);
		map.put(session3.pseudonym, session3);
	}
}
