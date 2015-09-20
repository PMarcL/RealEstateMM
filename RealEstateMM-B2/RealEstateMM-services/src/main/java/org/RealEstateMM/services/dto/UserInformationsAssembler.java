package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;

public class UserInformationsAssembler {

	public UserInformations toDTO(User user) {
		UserInformations dto = new UserInformations();
		dto.setEmail(user.email.toString());
		dto.setFirstName(user.name.firstName);
		dto.setLastName(user.name.lastName);
		dto.setPhoneNumber(user.phoneNumber.toString());
		dto.setPseudonym(user.pseudonym);
		return dto;
	}

	public User fromDTO(UserInformations userInfos) {
		Name name = new Name(userInfos.getFirstName(), userInfos.getLastName());
		Email email = new Email(userInfos.getEmail());
		PhoneNumber phoneNumber = new PhoneNumber(userInfos.getPhoneNumber());
		return new User(userInfos.getPseudonym(), userInfos.getPassword(), name, email, phoneNumber);
	}

}
