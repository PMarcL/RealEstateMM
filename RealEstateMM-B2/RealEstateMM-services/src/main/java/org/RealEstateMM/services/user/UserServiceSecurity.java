package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.user.dtos.UserDTO;
import org.RealEstateMM.services.user.validation.InvalidUserInformationsException;

public class UserServiceSecurity implements UserServiceHandler {

	private UserServiceHandler service;
	private UserAuthorizations authorizations;

	public UserServiceSecurity(UserServiceHandler service, UserAuthorizations authorizations) {
		this.service = service;
		this.authorizations = authorizations;
	}

	@Override
	public void createUser(UserDTO userDTO)
			throws ExistingUserException, EmailAddressConfirmationException, InvalidUserInformationsException {
		service.createUser(userDTO);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password)
			throws AuthenticationFailedException, InvalidUserInformationsException {
		return service.authenticate(pseudonym, password);
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException {
		service.confirmEmailAddress(confirmationCode);
	}

	@Override
	public void updateUserProfile(String pseudonym, UserDTO userProfile) throws ForbiddenAccessException,
			UserNotFoundException, EmailAddressConfirmationException, InvalidUserInformationsException {
		validateUserAccess(pseudonym, AccessLevel.BUYER, AccessLevel.SELLER);
		service.updateUserProfile(pseudonym, userProfile);
	}

	private void validateUserAccess(String pseudonym, AccessLevel... accessLevels) throws ForbiddenAccessException {
		if (!authorizations.isUserAuthorized(pseudonym, accessLevels)) {
			throw new ForbiddenAccessException();
		}
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException {
		return service.getUserProfile(pseudonym);
	}

}
