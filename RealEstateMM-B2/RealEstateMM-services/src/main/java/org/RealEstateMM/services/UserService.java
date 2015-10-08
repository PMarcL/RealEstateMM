package org.RealEstateMM.services;

import java.util.Optional;
import java.util.UUID;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.mail.CouldNotSendMailException;
import org.RealEstateMM.services.mail.GmailSender;
import org.RealEstateMM.services.mail.MailConfirmationSender;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class UserService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private MailConfirmationSender mailConfirmationSender;

	public UserService(UserRepository userRepository, UserAssembler accountAssembler,
			MailConfirmationSender mailConfirmationSender) {
		this.userRepository = userRepository;
		this.userAssembler = accountAssembler;
		this.mailConfirmationSender = mailConfirmationSender;
	}

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserAssembler();
		mailConfirmationSender = new GmailSender();
	}

	public void create(UserDTO userDTO) throws CouldNotSendMailException {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		sendEmailConfirmation(newUser);
	}

	private void sendEmailConfirmation(User newUser) {
		String email = newUser.getUserInformations().email;
		UUID emailConfirmationCode = newUser.getEmailConfirmationCode();
		mailConfirmationSender.send(email, emailConfirmationCode);
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
