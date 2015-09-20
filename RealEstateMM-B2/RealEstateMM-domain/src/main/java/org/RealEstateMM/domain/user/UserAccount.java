package org.RealEstateMM.domain.user;

public class UserAccount {
	public final String name;
	public final Email email;
	public final PhoneNumber phoneNumber;
	public final String pseudonym;
	public final String password;

	public UserAccount(String pseudonym, String password, String name, Email email, PhoneNumber phoneNumber) {
		this.pseudonym = pseudonym;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserAccount))
			return false;

		UserAccount userInfos = (UserAccount) object;
		return hasSameInformations(userInfos);
	}

	private boolean hasSameInformations(UserAccount user) {
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
