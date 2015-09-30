package org.RealEstateMM.authentication.session;

import java.util.Optional;

public interface SessionRepository {

	public void saveOrOverwriteSession(Session session);

	public void removeSesionWithToken(String aToken);

	public Optional<Session> getByToken(String token);

}