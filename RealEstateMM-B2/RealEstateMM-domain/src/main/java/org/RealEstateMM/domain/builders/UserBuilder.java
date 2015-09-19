package org.RealEstateMM.domain.builders;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;

public class UserBuilder {

	private String pseudonym;
	private Name name;
	private Email email;
	private PhoneNumber phoneNumber;

	public UserBuilder() {
	}

	public UserBuilder withPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
		return this;
	}

	public UserBuilder withPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserBuilder withName(Name name) {
		this.name = name;
		return this;
	}

	public UserBuilder withEmail(Email email) {
		this.email = email;
		return this;
	}

	public User build() {
		return new User(pseudonym, name, email, phoneNumber);
	}

}
