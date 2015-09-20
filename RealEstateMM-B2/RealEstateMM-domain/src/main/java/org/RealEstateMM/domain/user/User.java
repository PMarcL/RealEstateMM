package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;

public class User {

	public final String pseudonym;
	public final Name name;
	public final Email email;
	public final PhoneNumber phoneNumber;
	public final String password;

	public User(String pseudonym, String password, Name name, Email email, PhoneNumber phoneNumber) {
		this.pseudonym = pseudonym;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User))
			return false;

		User userInfos = (User) object;
		return hasSameInformations(userInfos);
	}

	private boolean hasSameInformations(User user) {
		boolean areEquals = this.email.equals(user.email);
		areEquals &= this.name.equals(user.name);
		areEquals &= this.phoneNumber.equals(user.phoneNumber);
		areEquals &= this.pseudonym.equals(user.pseudonym);
		areEquals &= this.password.equals(user.password);
		return areEquals;
	}

	public boolean hasPassword(String password) {
		return this.password.equals(password);
	}
}
