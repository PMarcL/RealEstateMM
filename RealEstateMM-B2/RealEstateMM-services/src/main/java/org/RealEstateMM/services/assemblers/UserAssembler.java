package org.RealEstateMM.services.assemblers;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;
import org.RealEstateMM.services.dtos.UserDTO;

public class UserAssembler {

	public UserDTO buildDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.email.toString());
		dto.setFirstName(user.name.firstName);
		dto.setLastName(user.name.lastName);
		dto.setPhoneNumber(user.phoneNumber.toString());
		dto.setPseudonym(user.pseudonym);
		return dto;
	}

	public User assemble(UserDTO dto) {
		String pseudonym = dto.getPseudonym();
		Name name = new Name(dto.getFirstName(), dto.getLastName());
		Email email = new Email(dto.getEmail());
		PhoneNumber phoneNumber = new PhoneNumber(dto.getPhoneNumber());
		return new User(pseudonym, name, email, phoneNumber);
	}

}
