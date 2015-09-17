package org.RealEstateMM.domain.user;

public class UserInformations {
	public final String name;
	public final String email;
	public final String phoneNumber;
	public final String pseudonym;

	public UserInformations(String pseudonym, String name, String email, String phoneNumber) {
		this.pseudonym = pseudonym;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public boolean equals(Object object) {
		if (!(object instanceof UserInformations))
			return false;

		UserInformations userInfos = (UserInformations) object;
		return hasSameInformations(userInfos);
	}

	private boolean hasSameInformations(UserInformations user) {
		boolean areEquals = this.email.equals(user.email);
		areEquals &= this.name.equals(user.name);
		areEquals &= this.phoneNumber.equals(user.phoneNumber);
		areEquals &= this.pseudonym.equals(user.pseudonym);
		return areEquals;
	}
}
