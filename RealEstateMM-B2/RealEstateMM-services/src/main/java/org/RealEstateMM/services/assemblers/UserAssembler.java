package org.RealEstateMM.services.assemblers;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;
import org.RealEstateMM.services.dto.UserDTO;

public class UserAssembler {

	public UserDTO buildDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.email = user.email.toString();
		dto.firstName = user.name.firstName;
		dto.lastName = user.name.lastName;
		dto.phoneNumber = user.phoneNumber.toString();
		dto.pseudonym = user.pseudonym;
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
