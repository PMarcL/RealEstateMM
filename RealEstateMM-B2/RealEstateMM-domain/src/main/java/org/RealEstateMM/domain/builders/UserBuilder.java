package org.RealEstateMM.domain.builders;

import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.Name;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.User;

public class UserBuilder {

	private String pseudonym;
	private Name name;
	private Email email;
	private PhoneNumber phoneNumber;

	public UserBuilder() {
	}

	public void setDefaults(String pseudo, Name name, Email email, PhoneNumber phoneNumber) {
		pseudonym = pseudo;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;

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
