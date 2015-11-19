package org.RealEstateMM.authentication.session;

import java.util.Optional;

public interface SessionRepository {

	public void addOrOverwriteSession(Session session);

	public void removeSesionWithToken(String token);

	public Optional<Session> getByToken(String token);

}