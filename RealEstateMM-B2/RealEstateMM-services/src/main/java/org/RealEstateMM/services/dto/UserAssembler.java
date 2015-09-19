package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;

public class UserAssembler {

	public UserDTO buildDTO(User userAccount) {
		UserDTO dto = new UserDTO();
		dto.email = userAccount.email.toString();
		dto.firstName = userAccount.name.firstName;
		dto.lastName = userAccount.name.lastName;
		dto.phoneNumber = userAccount.phoneNumber.toString();
		dto.pseudonym = userAccount.pseudonym;
		return dto;
	}

	public User assemble(UserDTO dto) {
		String pseudonym = dto.pseudonym;
		Name name = new Name(dto.firstName, dto.lastName);
		Email email = new Email(dto.email);
		PhoneNumber phoneNumber = new PhoneNumber(dto.phoneNumber);
		return new User(pseudonym, name, email, phoneNumber);
	}

}
