package org.RealEstateMM.services;

import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.DTO.UserCredentials;
import org.RealEstateMM.services.DTO.UserInformationsDTO;
import org.RealEstateMM.services.DTO.UserInformationsDTOAssembler;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserInformationsDTOAssembler userInfoDTOAssembler;

	public UserConnectionService() {

	}

	public UserConnectionService(UserRepository userRepo, UserInformationsDTOAssembler dtoAssembler) {
		userRepository = userRepo;
		userInfoDTOAssembler = dtoAssembler;
	}

	public UserInformationsDTO connectWithCredentials(UserCredentials credentials) {
		UserInformations userInfo = userRepository.getUserWithPseudoAndPassword(credentials.getPseudo(),
				credentials.getPassword());
		return userInfoDTOAssembler.buildDTO(userInfo);
	}

}
