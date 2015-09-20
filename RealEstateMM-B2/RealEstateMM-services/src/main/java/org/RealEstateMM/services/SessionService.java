package org.RealEstateMM.services;

import org.RealEstateMM.domain.session.Session;
import org.RealEstateMM.domain.session.SessionRepository;
import org.RealEstateMM.domain.user.User;

public class SessionService {

	private SessionRepository sessionRepository;

	public SessionService(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public Session createSession(User user) {
		sessionRepository.addSession(user, "some_token");
		return null;
	}

}
