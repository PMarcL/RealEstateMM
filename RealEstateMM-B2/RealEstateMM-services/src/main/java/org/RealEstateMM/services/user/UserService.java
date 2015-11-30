package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.user.AuthenticationFailedException;
import org.RealEstateMM.domain.user.EmailAddressConfirmationException;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.dtos.UserDTO;

public class UserService implements UserServiceHandler {

	private UserAssembler userAssembler;
	private Users users;

	public UserService(UserAssembler userAssembler) {
		this.userAssembler = userAssembler;
		users = ServiceLocator.getInstance().getService(Users.class);
	}

	@Override
	public void createUser(UserDTO userDTO) throws ExistingUserException, EmailAddressConfirmationException {
		User newUser = userAssembler.fromDTO(userDTO);
		users.addUser(newUser);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password) throws AuthenticationFailedException {
		User authendicatedUser = users.authenticate(pseudonym, password);
		return userAssembler.toDTO(authendicatedUser);
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException {
		users.confirmEmailAddress(confirmationCode);
	}

	@Override
	public void updateUserProfile(String pseudo, UserDTO userProfile)
			throws UserNotFoundException, EmailAddressConfirmationException {
		UserInformations userInfos = userAssembler.createUserInformations(userProfile);
		users.updateUserProfile(userInfos);
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException {
		User user = users.getUser(pseudonym);
		return userAssembler.toDTO(user);
	}

}
