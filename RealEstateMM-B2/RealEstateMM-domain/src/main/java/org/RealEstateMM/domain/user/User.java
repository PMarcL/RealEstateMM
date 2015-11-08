package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class User {

	private UserInformations userInformations;
	private UserRole role;
	private boolean isLocked;

	public User(UserInformations userInfo, UserRole role) {
		this.userInformations = userInfo;
		this.role = role;
		lock();
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

	public void lock() {
		isLocked = true;
	}

	public AccessLevel getRoleDescription() {
		return role.getRoleDescription();
	}

	public boolean isAuthorized(AccessLevel accessLevel) {
		return role.isAuthorized(accessLevel);
	}

}
