package org.RealEstateMM.context.demo;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.authentication.session.SessionService;

public class SessionServiceFactory {

	public SessionService create(SessionRepository sessionRepository) {
		return new SessionService(sessionRepository);
	}

}
