package org.RealEstateMM.domain.user;

public class User {

	private UserInformations userInformations;
	private UserType userType;
	private boolean isLocked;

	public User(UserInformations userInfo, UserType type) {
		this.userInformations = userInfo;
		this.userType = type;
		this.isLocked = true;
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

	public boolean isLocked() {
		return isLocked;
	}

	public void unlock() {
		isLocked = false;
	}

	public boolean hasEmailAddress(String emailAddress) {
		return userInformations.emailAddress.equals(emailAddress);
	}

	public void updateUserInformations(UserInformations userInformations) {
		this.userInformations = userInformations;
	}

}
