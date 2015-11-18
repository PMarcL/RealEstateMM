package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.services.user.validation.InvalidUserInformationsException;

public interface UserServiceHandler {

	public void createUser(UserDTO userDTO)
			throws ExistingUserException, EmailAddressConfirmationException, InvalidUserInformationsException;

	public UserDTO authenticate(String pseudonym, String password)
			throws AuthenticationFailedException, InvalidUserInformationsException;

	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException;

	public void updateUserProfile(String pseudo, UserDTO userProfile) throws ForbiddenAccessException,
			UserNotFoundException, EmailAddressConfirmationException, InvalidUserInformationsException;

	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException;
}
