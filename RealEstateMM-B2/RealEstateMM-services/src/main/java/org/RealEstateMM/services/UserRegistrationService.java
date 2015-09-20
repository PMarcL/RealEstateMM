package org.RealEstateMM.services;

import org.RealEstateMM.domain.session.Session;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;

public class UserRegistrationService {

	private UserRepository userRepository;
	private SessionService sessionService;
	private UserInformationsAssembler userAssembler;

	public UserRegistrationService(UserRepository userRepository, SessionService sessionService,
			UserInformationsAssembler userAssembler) {
		this.userRepository = userRepository;
		this.sessionService = sessionService;
		this.userAssembler = userAssembler;
	}

	// TODO verify this method (Session) --> weird
	public UserInformations register(UserInformations newUserInfos) {
		User newUser = userAssembler.fromDTO(newUserInfos);
		userRepository.addUser(newUser);
		Session session = sessionService.createSession(newUser);

		UserInformations connectedUser = userAssembler.toDTO(session.getUser());
		connectedUser.setToken(session.getToken());

		return connectedUser;
	}
}
