package org.RealEstateMM.services;

import org.RealEstateMM.domain.models.session.Session;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.UserRepository;
import org.RealEstateMM.services.assemblers.UserAssembler;
import org.RealEstateMM.services.dtos.UserDTO;

public class UserRegistrationService {

	private UserRepository userRepo;
	private SessionService sessionService;
	private UserAssembler userAssembler;

	public UserRegistrationService(UserRepository userRepoMock, SessionService sessionService,
			UserAssembler userAssembler) {
		this.userRepo = userRepoMock;
		this.sessionService = sessionService;
		this.userAssembler = userAssembler;
	}

	public UserDTO register(UserDTO newUserDTO) {
		User newUser = userAssembler.assemble(newUserDTO);
		User addedUser = userRepo.addUser(newUser);
		Session session = sessionService.createSession(addedUser);

		UserDTO connectedUser = userAssembler.buildDTO(session.getUser());
		connectedUser.setToken(session.getToken());

		return connectedUser;
	}
}
