package org.RealEstateMM.services.dtos.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;

public class UserAssembler {

	public UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		UserInformations userInfo = user.getUserInformations();
		dto.setEmail(userInfo.email);
		dto.setFirstName(userInfo.firstName);
		dto.setLastName(userInfo.lastName);
		dto.setPhoneNumber(userInfo.phoneNumber);
		dto.setPseudonym(user.getPseudonym());
		dto.setPassword(userInfo.password);
		dto.setUserType(user.getUserTypeDescription());
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		UserInformations userInfo = new UserInformations(userDTO.getPseudonym(), userDTO.getPassword(),
				userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber());
		UserType type = new UserType(userDTO.getUserType());
		return new User(userInfo, type);
	}
}
