package org.RealEstateMM.services.helpers;

import org.RealEstateMM.domain.helpers.DefaultUserValue;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class DefaultUserDTOBuilder {

	private String pseudonym;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public DefaultUserDTOBuilder() {
		this.pseudonym = DefaultUserValue.PSEUDO;
		this.firstName = DefaultUserValue.FIRST_NAME;
		this.lastName = DefaultUserValue.LAST_NAME;
		this.email = DefaultUserValue.EMAIL;
		this.phoneNumber = DefaultUserValue.PHONE_NUMBER;
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
