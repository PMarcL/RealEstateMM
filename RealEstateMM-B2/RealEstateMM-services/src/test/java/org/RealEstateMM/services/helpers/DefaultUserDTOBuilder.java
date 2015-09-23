package org.RealEstateMM.services.helpers;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.services.dto.UserDTO;

public class DefaultUserDTOBuilder {

	private String pseudonym;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public DefaultUserDTOBuilder() {
		this.pseudonym = DefaultUserBuilder.PSEUDO;
		this.firstName = DefaultUserBuilder.FIRST_NAME;
		this.lastName = DefaultUserBuilder.LAST_NAME;
		this.email = DefaultUserBuilder.EMAIL;
		this.phoneNumber = DefaultUserBuilder.PHONE_NUMBER;
	}

	public DefaultUserDTOBuilder withPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
		return this;
	}

	public DefaultUserDTOBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public DefaultUserDTOBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public DefaultUserDTOBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public DefaultUserDTOBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserDTO build() {
		return new UserDTO(pseudonym, firstName, lastName, email, phoneNumber);
	}

}
