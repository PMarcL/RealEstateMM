package org.RealEstateMM.services;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;

public class UserRegistrationService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;

	public UserRegistrationService(UserRepository userRepoMock, UserAssembler userAssembler) {
		this.userRepository = userRepoMock;
		this.userAssembler = userAssembler;
	}

	public void register(UserDTO newUserDTO) {
		User newUser = userAssembler.assemble(newUserDTO);
		userRepository.create(newUser);
	}
}
