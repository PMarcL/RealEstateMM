package org.RealEstateMM.domain.user;

public class UserInformations {
	public String name;
	public String email;
	public String phoneNumber;
	public String pseudonym;

	public boolean equals(Object object) {
		if (object instanceof UserInformations && hasSameInformations((UserInformations) object)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hasSameInformations(UserInformations user) {
		boolean areEquals = false;
		areEquals = this.email.equals(user.email);
		areEquals = this.name.equals(user.name);
		areEquals = this.phoneNumber.equals(user.phoneNumber);
		areEquals = this.pseudonym.equals(user.pseudonym);
		return areEquals;
	}
}
