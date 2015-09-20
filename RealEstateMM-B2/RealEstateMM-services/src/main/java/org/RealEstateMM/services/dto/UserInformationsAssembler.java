package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;

public class UserInformationsAssembler {

	public UserInformations toDTO(User user) {
		UserInformations dto = new UserInformations();
		dto.email = user.email.toString();
		dto.firstName = user.name.firstName;
		dto.lastName = user.name.lastName;
		dto.phoneNumber = user.phoneNumber.toString();
		dto.pseudonym = user.pseudonym;
		return dto;
	}

	public User fromDTO(UserInformations userInfos) {
		Name name = new Name(userInfos.firstName, userInfos.lastName);
		Email email = new Email(userInfos.email);
		PhoneNumber phoneNumber = new PhoneNumber(userInfos.phoneNumber);
		return new User(userInfos.pseudonym, userInfos.password, name, email, phoneNumber);
	}

}
