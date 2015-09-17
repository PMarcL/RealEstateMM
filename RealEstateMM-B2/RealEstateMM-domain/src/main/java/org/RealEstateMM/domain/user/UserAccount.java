package org.RealEstateMM.domain.user;

public class UserAccount {
	public final String name;
	public final String email; // TODO create Email class
	public final String phoneNumber; // TODO create PhoneNumber class
	public final String pseudonym;

	public UserAccount(String pseudonym, String name, String email, String phoneNumber) {
		this.pseudonym = pseudonym;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

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
		return areEquals;
	}
}
