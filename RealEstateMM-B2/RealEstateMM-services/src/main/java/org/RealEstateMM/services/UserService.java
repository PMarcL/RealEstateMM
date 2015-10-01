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

	public void create(UserDTO userDTO) {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		userAssembler.toDTO(newUser);
	}

	public UserDTO authenticate(String pseudonym, String password)
			throws InvalidPasswordException, UserDoesNotExistException {

		Optional<User> userOptional = userRepository.getUserWithPseudonym(pseudonym);
		if (userOptional.isPresent()) {
			if (userOptional.get().hasPassword(password)) {
				return userAssembler.toDTO(userOptional.get());
			}
			throw new InvalidPasswordException();
		}
		throw new UserDoesNotExistException();
	}

}
