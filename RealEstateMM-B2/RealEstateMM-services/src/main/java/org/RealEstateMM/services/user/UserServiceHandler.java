package org.RealEstateMM.services.user;

import org.RealEstateMM.services.user.dtos.UserDTO;

public interface UserServiceHandler {

	public void createUser(UserDTO userDTO);

	public UserDTO authenticate(String pseudonym, String password);

	public void confirmEmailAddress(String confirmationCode);

	public void updateUserProfile(String pseudo, UserDTO userProfile) throws ForbiddenAccessException;

	public UserDTO getUserProfile(String pseudonym);
}
