package org.RealEstateMM.services.helpers;

import org.RealEstateMM.domain.helpers.DefaultUserValue;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class DefaultUserDTOBuilder {

	private String pseudonym;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private String userType;

	public DefaultUserDTOBuilder() {
		this.pseudonym = DefaultUserValue.PSEUDO;
		this.password = DefaultUserValue.PASSWORD;
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

	public DefaultUserDTOBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public DefaultUserDTOBuilder withUserType(String userType) {
		this.userType = userType;
		return this;
	}

	public UserDTO build() {
		return new UserDTO(pseudonym, password, firstName, lastName, email, phoneNumber, userType);
	}

}
