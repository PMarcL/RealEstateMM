package org.RealEstateMM.services;

import java.util.Optional;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAccountAssembler;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserInformations;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserAccountAssembler userInfoDTOAssembler;

	public UserConnectionService(UserRepository userRepo, UserAccountAssembler dtoAssembler) {
		userRepository = userRepo;
		userInfoDTOAssembler = dtoAssembler;
	}

	public UserInformations connectWithCredentials(UserCredentials credentials) {
		Optional<UserAccount> userAccount = userRepository.getUserWithPseudonym(credentials.getPseudo());
		validateUserAccountCredentials(credentials, userAccount);
		return userInfoDTOAssembler.buildDTO(userAccount.get());
	}

	private void validateUserAccountCredentials(UserCredentials credentials, Optional<UserAccount> userAccount) {
		if (userDoesNotExist(userAccount)) {
			throw new UserNotFoundException();
		}
		if (!userAccount.get().hasPassword(credentials.getPassword())) {
			throw new ErronousPasswordException();
		}
	}

	private boolean userDoesNotExist(Optional<UserAccount> userAccount) {
		return !userAccount.isPresent();
	}

}
