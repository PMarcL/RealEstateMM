package org.RealEstateMM.services.user;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.mail.CouldNotSendMailException;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;
import org.RealEstateMM.services.user.mailconfirmation.EmailConfirmationService;

public class UserService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private EmailConfirmationService emailConfirmationService;

	public UserService(UserRepository userRepository, UserAssembler accountAssembler,
			EmailConfirmationService mailConfirmationService) {
		this.userRepository = userRepository;
		this.userAssembler = accountAssembler;
		this.emailConfirmationService = mailConfirmationService;
	}

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserAssembler();
		emailConfirmationService = new EmailConfirmationService();
	}

	public void create(UserDTO userDTO) throws CouldNotSendMailException {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		emailConfirmationService.sendEmailConfirmation(newUser);
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

	public void confirmEmailAddress(String confirmationCode) {
		emailConfirmationService.getConfirmingUserPseudonym(confirmationCode);
	}

}
