package org.RealEstateMM.services.dtos.user;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserBuilder;

public class UserAssembler {

	public UserDTO toDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail().toString());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setPhoneNumber(user.getPhoneNumber().toString());
		dto.setPseudonym(user.getPseudonym());
		dto.setPassword(user.getPassword());
		return dto;
	}

	public User fromDTO(UserDTO userDTO) {
		return new UserBuilder(userDTO.getPseudonym(), userDTO.getPassword(), userDTO.getFirstName(),
				userDTO.getLastName(), userDTO.getEmail(), userDTO.getPhoneNumber()).build();
	}

}
