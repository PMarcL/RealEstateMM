package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.user.UserAccount;

public class UserAccountAssembler {

	public UserInformations buildDTO(UserAccount userAccount) {
		UserInformations dto = new UserInformations();
		dto.email = userAccount.email.toString();
		dto.name = userAccount.name;
		dto.phoneNumber = userAccount.phoneNumber.toString();
		dto.pseudonym = userAccount.pseudonym;
		return dto;
	}

}
