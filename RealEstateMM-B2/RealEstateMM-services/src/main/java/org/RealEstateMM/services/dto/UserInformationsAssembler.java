package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.user.User;

public class UserInformationsAssembler {

	public UserInformations toDTO(User user) {
		UserInformations dto = new UserInformations();
		dto.setEmail(user.email.toString());
		dto.setFirstName(user.firstName);
		dto.setLastName(user.lastName);
		dto.setPhoneNumber(user.phoneNumber.toString());
		dto.setPseudonym(user.pseudonym);
		return dto;
	}

	public User fromDTO(UserInformations userInfos) {
		// TODO when the password is removed from the User class, remove the
		// password string here
		return new User(userInfos.getPseudonym(), "12345", userInfos.getFirstName(), userInfos.getLastName(),
				userInfos.getEmail(), userInfos.getPhoneNumber());
	}

}
