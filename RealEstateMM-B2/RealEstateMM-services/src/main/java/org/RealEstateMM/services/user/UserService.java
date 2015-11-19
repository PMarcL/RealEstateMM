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

	private UserAssembler assembler;
	private Users users;

	public UserService() {
		assembler = ServiceLocator.getInstance().getService(UserAssembler.class);
		users = ServiceLocator.getInstance().getService(Users.class);
	}

	@Override
	public void createUser(UserDTO userDTO) throws ExistingUserException, EmailAddressConfirmationException {
		User newUser = assembler.fromDTO(userDTO);
		users.addUser(newUser);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password) throws AuthenticationFailedException {
		User authendicatedUser = users.authenticate(pseudonym, password);
		return assembler.toDTO(authendicatedUser);
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws EmailAddressConfirmationException {
		users.confirmEmailAddress(confirmationCode);
	}

	@Override
	public void updateUserProfile(String pseudo, UserDTO userProfile)
			throws UserNotFoundException, EmailAddressConfirmationException {
		UserInformations userInfos = assembler.createUserInformations(userProfile);
		users.updateUserProfile(userInfos);
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) throws UserNotFoundException {
		User user = users.getUser(pseudonym);
		return assembler.toDTO(user);
	}

}