package org.RealEstateMM.services.user.dtos;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRole;
import org.RealEstateMM.domain.user.UserRoleFactory;

public class UserAssembler {

	private UserRoleFactory roleFactory;

	public UserAssembler(UserRoleFactory roleFactory) {
		this.roleFactory = roleFactory;
	}

	public UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		UserInformations userInfo = user.getUserInformations();
		dto.setEmail(userInfo.emailAddress);
		dto.setFirstName(userInfo.firstName);
		dto.setLastName(userInfo.lastName);
		dto.setPhoneNumber(userInfo.phoneNumber);
		dto.setPseudonym(user.getPseudonym());
		dto.setPassword(userInfo.password);
		dto.setUserType(user.getRoleDescription().toString());
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		UserInformations userInfo = createUserInformations(userDTO);
		UserRole role = roleFactory.create(userDTO.getUserRole());
		return new User(userInfo, role);
	}

	private UserInformations createUserInformations(UserDTO userDTO) {
		return new UserInformations(userDTO.getPseudonym(), userDTO.getPassword(), userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getEmailAddress(), userDTO.getPhoneNumber());
	}
}
