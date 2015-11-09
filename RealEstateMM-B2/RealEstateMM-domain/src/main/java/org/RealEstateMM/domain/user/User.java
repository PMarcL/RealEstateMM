package org.RealEstateMM.domain.user;

import java.util.Date;

import org.RealEstateMM.domain.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.domain.user.exceptions.UnconfirmedEmailException;

public class User {

	private UserInformations userInformations;
	private UserType userType;

	private Date lastLoginDate;
	private boolean isLocked;

	public User(UserInformations userInfo, UserType type) {
		this.userInformations = userInfo;
		this.userType = type;
		lock();
	}

	public void authenticate(String password) throws UnconfirmedEmailException, InvalidPasswordException {
		if (isLocked()) {
			throw new UnconfirmedEmailException();
		}
		if (!hasPassword(password)) {
			throw new InvalidPasswordException();
		}
		login();
	}

	private void login() {
		this.lastLoginDate = new Date();
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

	public Date getLastLoginDate() {
		return lastLoginDate;
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

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
