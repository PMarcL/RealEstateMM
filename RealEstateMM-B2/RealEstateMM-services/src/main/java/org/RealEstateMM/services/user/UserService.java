package org.RealEstateMM.services.user;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.emailconfirmation.AlreadyConfirmedEmailAddressException;
import org.RealEstateMM.domain.user.emailconfirmation.EmailConfirmer;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.emailsender.CouldNotSendMailException;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UnconfirmedEmailException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;

public class UserService {

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private EmailConfirmer emailAddressConfirmer;

	public UserService(UserRepository userRepository, UserAssembler accountAssembler, EmailConfirmer emailConfirmer) {
		this.userRepository = userRepository;
		this.userAssembler = accountAssembler;
		this.emailAddressConfirmer = emailConfirmer;
	}

	public UserService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		userAssembler = new UserAssembler();
		emailAddressConfirmer = ServiceLocator.getInstance().getService(EmailConfirmer.class);
	}

	public void create(UserDTO userDTO) throws CouldNotSendMailException {
		User newUser = userAssembler.fromDTO(userDTO);
		userRepository.addUser(newUser);
		emailAddressConfirmer.sendEmailConfirmation(newUser);
	}

	public UserDTO authenticate(String pseudonym, String password)
			throws InvalidPasswordException, UserDoesNotExistException, UnconfirmedEmailException {

		Optional<User> userOptional = userRepository.getUserWithPseudonym(pseudonym);
		if (userOptional.isPresent()) {
			if(userOptional.get().isLocked())
			{
				throw new UnconfirmedEmailException();
			}
			if (userOptional.get().hasPassword(password)) {
				return userAssembler.toDTO(userOptional.get());
			}
			throw new InvalidPasswordException();
		}
		throw new UserDoesNotExistException();
	}

	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		try {
			emailAddressConfirmer.confirmEmailAddress(confirmationCode);
		} catch (InvalidEmailConfirmationCodeException | AlreadyConfirmedEmailAddressException exception) {
			throw new ImpossibleToConfirmEmailAddressException(exception);
		}
	}

}
