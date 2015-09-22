package org.RealEstateMM.services;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class UserRegistrationService {

	private UserRepository userRepository;
	private UserInformationsAssembler userAssembler;

	public UserRegistrationService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserInformationsAssembler();
	}

	public UserRegistrationService(UserRepository userRepository, UserInformationsAssembler userAssembler) {
		this.userRepository = userRepository;
		this.userAssembler = userAssembler;
	}

	public void register(UserInformations newUserInfos) {
		User newUser = userAssembler.fromDTO(newUserInfos);
		userRepository.addUser(newUser);
	}
}
