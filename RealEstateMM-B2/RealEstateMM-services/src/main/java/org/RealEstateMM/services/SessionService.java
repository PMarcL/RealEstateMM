package org.RealEstateMM.services;

import org.RealEstateMM.domain.models.session.Session;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.SessionRepository;

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
