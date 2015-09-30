package org.RealEstateMM.authentication.session;

import java.util.Optional;
import java.util.UUID;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class SessionService {

	private SessionRepository sessionRepository;
	private UserAssembler userAssembler;

	public SessionService() {
		this.sessionRepository = new SessionRepository() {

			@Override
			public void saveOrOverwriteSession(Session session) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeSesionWithToken(String aToken) {
				// TODO Auto-generated method stub

			}

			@Override
			public Optional<Session> getByToken(String token) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		this.userAssembler = new UserAssembler();
	}

	public SessionService(SessionRepository sessionRepository, UserAssembler userAssembler) {
		this.sessionRepository = sessionRepository;
		this.userAssembler = userAssembler;
	}

	public Session open(UserDTO userDTO) {
		User user = userAssembler.fromDTO(userDTO);

		Session createdSession = createASession(user);
		sessionRepository.saveOrOverwriteSession(createdSession);

		return createdSession;
	}

	private Session createASession(User user) {
		String token = UUID.randomUUID().toString();
		return new Session(user.getPseudonym(), token);
	}

	public void close(String token) {
		sessionRepository.removeSesionWithToken(token);
	}

	public String validate(String token) throws TokenInvalidException {
		Optional<Session> session = sessionRepository.getByToken(token);
		if (session.isPresent()) {
			return session.get().pseudonym;
		} else {
			throw new TokenInvalidException();
		}
	}

}
