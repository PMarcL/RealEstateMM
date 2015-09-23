package org.RealEstateMM.services.dtos.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserBuilder;

public class UserAssembler {

	public UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.email.toString());
		dto.setFirstName(user.firstName);
		dto.setLastName(user.lastName);
		dto.setPhoneNumber(user.phoneNumber.toString());
		dto.setPseudonym(user.pseudonym);
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		return new UserBuilder(userDTO.getPseudonym(), userDTO.getFirstName(), userDTO.getLastName(),
				userDTO.getEmail(), userDTO.getPhoneNumber()).build();
	}

}
