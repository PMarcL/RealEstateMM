package org.RealEstateMM.authentication.session;

import java.util.Optional;
import java.util.UUID;

import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.user.dtos.UserDTO;

public class SessionService {

	private SessionRepository sessionRepository;

	public SessionService() {
		this.sessionRepository = ServiceLocator.getInstance().getService(SessionRepository.class);
	}

	public Session open(UserDTO userDTO) {
		Session createdSession = createASession(userDTO);
		sessionRepository.addOrOverwriteSession(createdSession);

		return createdSession;
	}

	private Session createASession(UserDTO userDTO) {
		String token = UUID.randomUUID().toString();
		return new Session(userDTO.getPseudonym(), token);
	}

	public void close(String token) {
		sessionRepository.removeSesionWithToken(token);
	}

	public String validate(String token) throws InvalidSessionTokenException {
		Optional<Session> session = sessionRepository.getByToken(token);
		if (session.isPresent()) {
			return session.get().pseudonym;
		} else {
			throw new InvalidSessionTokenException();
		}
	}

}
