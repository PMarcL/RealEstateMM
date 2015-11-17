package org.RealEstateMM.services.user;

import org.RealEstateMM.domain.user.InvalidPasswordException;
import org.RealEstateMM.domain.user.UnconfirmedEmailException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.domain.user.emailconfirmation.ImpossibleToConfirmEmailAddressException;
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
	public void createUser(UserDTO userDTO) {
		User newUser = assembler.fromDTO(userDTO);
		users.addUser(newUser);
	}

	@Override
	public UserDTO authenticate(String pseudonym, String password)
			throws UnconfirmedEmailException, InvalidPasswordException {
		User authendicatedUser = users.authenticate(pseudonym, password);
		return assembler.toDTO(authendicatedUser);
	}

	@Override
	public void confirmEmailAddress(String confirmationCode) throws ImpossibleToConfirmEmailAddressException {
		users.confirmEmailAddress(confirmationCode);
	}

	@Override
	public void updateUserProfile(String pseudo, UserDTO userProfile) {
		UserInformations userInfos = assembler.createUserInformations(userProfile);
		users.updateUserProfile(userInfos);
	}

	@Override
	public UserDTO getUserProfile(String pseudonym) {
		User user = users.getUser(pseudonym);
		return assembler.toDTO(user);
	}

}
