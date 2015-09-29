package org.RealEstateMM.services.helpers;

import org.RealEstateMM.domain.helpers.UserBuilder;
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
		this.pseudonym = UserBuilder.DEFAULT_PSEUDO;
		this.password = UserBuilder.DEFAULT_PASSWORD;
		this.firstName = UserBuilder.DEFAULT_FIRST_NAME;
		this.lastName = UserBuilder.DEFAULT_LAST_NAME;
		this.email = UserBuilder.DEFAULT_EMAIL;
		this.phoneNumber = UserBuilder.DEFAULT_PHONE_NUMBER;
		this.userType = UserBuilder.DEFAULT_USER_TYPE_DESC;
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
