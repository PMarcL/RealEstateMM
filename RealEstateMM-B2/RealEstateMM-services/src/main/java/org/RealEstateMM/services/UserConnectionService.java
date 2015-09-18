package org.RealEstateMM.services;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserAccountAssembler;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserAccountAssembler userInfoDTOAssembler;

	public UserConnectionService(UserRepository userRepo, UserAccountAssembler dtoAssembler) {
		userRepository = userRepo;
		userInfoDTOAssembler = dtoAssembler;
	}

	public UserInformations connectWithCredentials(UserCredentials credentials) {
		UserAccount userInfo = userRepository.getUserWithPseudoAndPassword(credentials.getPseudo(),
				credentials.getPassword());
		return userInfoDTOAssembler.buildDTO(userInfo);
	}

}
