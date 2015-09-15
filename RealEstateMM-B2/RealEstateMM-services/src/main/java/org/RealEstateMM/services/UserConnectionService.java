package org.RealEstateMM.services;

import org.RealEstateMM.domain.user.UserConnector;
import org.RealEstateMM.domain.user.UserInformation;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.DTO.UserCredentials;
import org.RealEstateMM.services.DTO.UserDTO;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserConnector userConnector;

	public UserConnectionService() {

	}

	public UserConnectionService(UserRepository userRepo, UserConnector userConnect) {
		userRepository = userRepo;
		userConnector = userConnect;
	}

	public UserDTO connectWithCredentials(UserCredentials credentials) {
		UserInformation userInfo = userRepository.getUserWithPseudoAndEmail(credentials.getPseudo(),
				credentials.getEmail());
		userConnector.ConnectUser(userInfo);
		return null;
	}

}
