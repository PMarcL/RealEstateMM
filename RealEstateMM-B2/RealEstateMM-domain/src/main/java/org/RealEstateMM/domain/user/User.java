package org.RealEstateMM.domain.user;

import java.util.UUID;

public class User {

	private UserInformations userInformations;
	private UserType userType;
	private UUID emailConfirmationCode;

	public User(UserInformations userInfo, UserType type) {
		this.userInformations = userInfo;
		this.userType = type;
	}

	public UserInformations getUserInformations() {
		return userInformations;
	}

	public String getPseudonym() {
		return userInformations.pseudonym;
	}

	public boolean hasPassword(String password) {
		return userInformations.password.equals(password);
	}

	public String getUserTypeDescription() {
		return userType.userTypeDescription;
	}

	public UUID getEmailConfirmationCode() {
		return emailConfirmationCode;
	}

	public boolean isLocked() {
		return true;
	}

}
