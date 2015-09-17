package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.user.UserAccount;

public class UserAccountAssembler {

	public UserInformations buildDTO(UserAccount userInfo) {
		UserInformations dto = new UserInformations();
		dto.email = userInfo.email;
		dto.name = userInfo.name;
		dto.phoneNumber = userInfo.phoneNumber;
		dto.pseudonym = userInfo.pseudonym;
		return dto;
	}

}
