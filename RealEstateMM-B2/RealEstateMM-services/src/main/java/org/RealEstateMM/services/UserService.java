package org.RealEstateMM.services;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
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

		User user = findUserWithPseudonym(pseudonym);
		if (!(user.hasPassword(password))) {
			throw new InvalidPasswordException();
		}

		return userAssembler.toDTO(user);
	}

	private User findUserWithPseudonym(String pseudonym) throws UserDoesNotExistException {
		Optional<User> user = userRepository.getUserWithPseudonym(pseudonym);
		if (!(user.isPresent())) {
			throw new UserDoesNotExistException();
		}
		return user.get();
	}

	public void updateUserProfile(UserDTO userProfile) throws UserDoesNotExistException {
		User user = findUserWithPseudonym(userProfile.getPseudonym());
		user.updateUserInformations(createUserInfosFromDTO(userProfile));
		userRepository.replaceUser(user);
	}

	private UserInformations createUserInfosFromDTO(UserDTO userProfile) {
		return new UserInformations(userProfile.getPseudonym(), userProfile.getPassword(), userProfile.getFirstName(),
				userProfile.getLastName(), userProfile.getEmail(), userProfile.getPhoneNumber());
	}

}
