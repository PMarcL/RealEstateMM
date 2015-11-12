package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.domain.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.domain.user.exceptions.UnconfirmedEmailException;

public interface UserServiceHandler {

	public void createUser(UserDTO userDTO) throws CouldNotSendMailException;

	public UserDTO authenticate(String pseudonym, String password) throws InvalidPasswordException,
			UserNotFoundException, UnconfirmedEmailException;

	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException;

	public void updateUserProfile(String pseudo, UserDTO userProfile) throws UserNotFoundException;

	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException;
}
