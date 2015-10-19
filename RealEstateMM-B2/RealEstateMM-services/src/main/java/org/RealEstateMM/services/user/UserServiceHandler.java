package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UnconfirmedEmailException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;

public interface UserServiceHandler {

	public void createUser(UserDTO userDTO) throws CouldNotSendMailException;

	public UserDTO authenticate(String pseudonym, String password) throws InvalidPasswordException,
			UserDoesNotExistException, UnconfirmedEmailException;

	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException;

	public void updateUserProfile(UserDTO userProfile) throws UserDoesNotExistException;

	public UserDTO getUserProfile(String pseudonym) throws UserDoesNotExistException;
}
