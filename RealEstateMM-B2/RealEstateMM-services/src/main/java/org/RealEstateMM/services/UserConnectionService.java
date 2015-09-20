package org.RealEstateMM.services;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserCredentials;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;

public class UserConnectionService {

	private UserRepository userRepository;
	private UserInformationsAssembler assembler;

	public UserConnectionService(UserRepository userRepository, UserInformationsAssembler assembler) {
		this.userRepository = userRepository;
		this.assembler = assembler;
	}

	public UserInformations connectWithCredentials(UserCredentials credentials) {
		Optional<User> user = userRepository.getUserWithPseudonym(credentials.getPseudo());
		validateUserCredentials(credentials, user);
		return assembler.toDTO(user.get());
	}

	private void validateUserCredentials(UserCredentials credentials, Optional<User> userAccount) {
		if (userDoesNotExist(userAccount)) {
			throw new UserNotFoundException();
			// TODO verify if this should go into the UserRepository instead?
		}
		if (!userAccount.get().hasPassword(credentials.getPassword())) {
			throw new ErronousPasswordException();
		}
	}

	private boolean userDoesNotExist(Optional<User> user) {
		return !user.isPresent();
	}

}
