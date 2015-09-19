package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;

public class UserAssembler {

	public UserDTO buildDTO(User userAccount) {
		UserDTO dto = new UserDTO();
		dto.setEmail(userAccount.email.toString());
		dto.setFirstName(userAccount.name.firstName);
		dto.setLastName(userAccount.name.lastName);
		dto.setPhoneNumber(userAccount.phoneNumber.toString());
		dto.setPseudonym(userAccount.pseudonym);
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
