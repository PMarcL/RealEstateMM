package org.RealEstateMM.services.DTO;

import org.RealEstateMM.domain.user.UserInformations;

public class UserInformationsDTOAssembler {

	public UserInformationsDTO buildDTO(UserInformations userInfo) {
		UserInformationsDTO dto = new UserInformationsDTO();
		dto.email = userInfo.email;
		dto.name = userInfo.name;
		dto.phoneNumber = userInfo.phoneNumber;
		dto.pseudonym = userInfo.pseudonym;
		return dto;
	}

}
