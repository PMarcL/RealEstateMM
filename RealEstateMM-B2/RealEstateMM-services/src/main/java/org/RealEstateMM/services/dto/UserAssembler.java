package org.RealEstateMM.services.dto;

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

	public User fromDTO(UserDTO userInfos) {
		return new UserBuilder(userInfos.getPseudonym(), userInfos.getFirstName(), userInfos.getLastName(),
				userInfos.getEmail(), userInfos.getPhoneNumber()).build();
	}

}
