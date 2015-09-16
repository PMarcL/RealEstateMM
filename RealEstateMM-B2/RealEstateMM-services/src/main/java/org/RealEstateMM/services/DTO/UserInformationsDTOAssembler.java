package org.RealEstateMM.services.DTO;

import org.RealEstateMM.domain.user.UserInformations;

public class UserInformationsDTOAssembler {

	public UserInformationsDTO buildDTO(UserInformations userInfo) {
		UserInformationsDTO dto = new UserInformationsDTO();
		dto.setEmail(userInfo.email);
		dto.setName(userInfo.name);
		dto.setPhoneNumber(userInfo.phoneNumber);
		dto.setPseudonym(userInfo.pseudonym);
		return dto;
	}

}
