package org.RealEstateMM.domain.user;

import java.util.Calendar;
import java.util.Date;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class User {

	private UserInformations userInformations;
	private UserRole role;
	private Date lastLoginDate;
	private boolean isLocked;

	public User(UserInformations userInfo, UserRole role) {
		this.userInformations = userInfo;
		this.role = role;
		this.lastLoginDate = Calendar.getInstance().getTime();
		lock();
	}

	public void authenticate(String password) {
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

	public AccessLevel getRoleDescription() {
		return role.getRoleDescription();
	}

	public boolean isAuthorized(AccessLevel accessLevel) {
		return role.isAuthorized(accessLevel);
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean hasLoggedLastAfter(Date when) {
		Calendar lastLoginCalendar = Calendar.getInstance();
		Calendar whenCalendar = Calendar.getInstance();

		lastLoginCalendar.setTime(lastLoginDate);
		whenCalendar.setTime(when);

		return lastLoginCalendar.after(whenCalendar);
	}

}
