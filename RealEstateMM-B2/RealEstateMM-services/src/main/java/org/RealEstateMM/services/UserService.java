package org.RealEstateMM.services;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class UserService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;

	public UserService(UserRepository userRepository, UserAssembler accountAssembler) {
		this.userRepository = userRepository;
		this.userAssembler = accountAssembler;
	}

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserAssembler();
	}

	public void createUser(UserDTO userDTO) {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
	}

	public String findUserType(String pseudonym, String password) throws Exception {
		Optional<User> userOptional = userRepository.getUserWithPseudonym(pseudonym);
		if (userOptional.isPresent()) {
			if (userOptional.get().hasPassword(password)) {
				return userOptional.get().getUserTypeDescription();
			}
			throw new InvalidPasswordException();
		}
		throw new UserDoesNotExistException();
	}

}
