package org.RealEstateMM.services;

import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserDTO;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserAssembler userInfoDTOAssembler;

	public UserConnectionService(UserRepository userRepo, UserAssembler dtoAssembler) {
		userRepository = userRepo;
		userInfoDTOAssembler = dtoAssembler;
	}

	public UserDTO connectWithCredentials(UserCredentials credentials) {
		User userInfo = userRepository.getUserWithPseudoAndPassword(credentials.getPseudo(), credentials.getPassword());
		return userInfoDTOAssembler.buildDTO(userInfo);
	}

}
