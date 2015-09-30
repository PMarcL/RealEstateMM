package org.RealEstateMM.persistence.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.authentication.session.Session;
import org.RealEstateMM.authentication.session.SessionRepository;

public class InMemorySessionRepository implements SessionRepository {

	private Map<String, Session> map;

	public InMemorySessionRepository() {
		map = new HashMap<String, Session>();
	}

	public InMemorySessionRepository(Map<String, Session> map) {
		this.map = map;
	}

	@Override
	public void saveOrOverwriteSession(Session session) {
		map.put(session.pseudonym, session);
	}

	@Override
	public void removeSesionWithToken(String token) {
		Optional<Session> session = getByToken(token);
		if (session.isPresent()) {
			map.remove(session.get().pseudonym);
		}
	}

	@Override
	public Optional<Session> getByToken(String token) {
		for (Session s : map.values()) {
			if (s.token.equals(token)) {
				return Optional.of(s);
			}
		}

		return Optional.empty();
	}

}
