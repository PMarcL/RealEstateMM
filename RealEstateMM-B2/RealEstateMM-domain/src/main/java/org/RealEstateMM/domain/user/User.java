package org.RealEstateMM.domain.user;

public class User {

	public final String pseudonym;
	public final Name name;
	public final Email email;
	public final PhoneNumber phoneNumber;

	public User(String pseudonym, Name name, Email email, PhoneNumber phoneNumber) {
		this.pseudonym = pseudonym;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
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
		return areEquals;
	}
}
