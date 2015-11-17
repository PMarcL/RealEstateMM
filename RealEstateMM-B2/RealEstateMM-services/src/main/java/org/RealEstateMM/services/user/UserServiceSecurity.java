package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.emailsender.CouldNotSendMailException;
import org.RealEstateMM.domain.user.InvalidPasswordException;
import org.RealEstateMM.domain.user.UnconfirmedEmailException;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.domain.user.emailconfirmation.ImpossibleToConfirmEmailAddressException;
import org.RealEstateMM.services.user.dtos.UserDTO;

public class UserServiceSecurity implements UserServiceHandler {

	private UserServiceHandler service;
	private UserAuthorizations authorizations;

	public UserServiceSecurity(UserServiceHandler service, UserAuthorizations authorizations) {
		this.service = service;
		this.authorizations = authorizations;
	}

	@Override
	public void createUser(UserDTO userDTO) throws CouldNotSendMailException {
		service.createUser(userDTO);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password)
			throws InvalidPasswordException, UserNotFoundException, UnconfirmedEmailException {
		return service.authenticate(pseudonym, password);
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		service.confirmEmailAddress(confirmationCode);
	}

	@Override
	public void updateUserProfile(String pseudonym, UserDTO userProfile)
			throws UserNotFoundException, ForbiddenAccessException {
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
