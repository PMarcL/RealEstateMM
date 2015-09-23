package org.RealEstateMM.services;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class UserRegistrationService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;

	public UserRegistrationService(UserRepository userRepository, UserAssembler userAssembler) {
		userAssembler = new UserInformationsAssembler();
	}

	public UserRegistrationService(UserRepository userRepository, UserInformationsAssembler userAssembler) {
		this.userRepository = userRepository;
		this.userAssembler = userAssembler;
	}

	public UserDTO register(UserDTO newUserInfos) {
		User newUser = userAssembler.fromDTO(newUserInfos);
		User storedUser = userRepository.addUser(newUser);
		return userAssembler.toDTO(storedUser);
	}
}
