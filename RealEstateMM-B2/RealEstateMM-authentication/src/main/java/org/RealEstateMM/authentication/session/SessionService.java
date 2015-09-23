package org.RealEstateMM.authentication.session;

import java.util.UUID;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;

public class SessionService {

	private SessionRepository sessionRepository;
	private UserAssembler userAssembler;

	public SessionService(SessionRepository sessionRepository, UserAssembler userAssembler) {
		this.sessionRepository = sessionRepository;
		this.userAssembler = userAssembler;
	}

	public Session openSession(UserDTO userDTO) {
		User user = userAssembler.fromDTO(userDTO);

		Session createdSession = createASession(user);
		sessionRepository.saveOrOverwriteSession(createdSession);

		return createdSession;
	}

	private Session createASession(User user) {
		String token = UUID.randomUUID().toString();
		return new Session(user.pseudonym, token);
	}

}
