package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.emailconfirmation.AlreadyConfirmedEmailAddressException;

public class User {

	private UserInformations userInformations;
	private UserType userType;
	private boolean isLocked;

	public User(UserInformations userInfo, UserType type) {
		this.userInformations = userInfo;
		this.userType = type;
		this.lock();
	}

	public User(UserInformations userInformations, UserType userType, boolean isLocked) {
		super();
		this.userInformations = userInformations;
		this.userType = userType;
		this.isLocked = isLocked;
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

	public String getEmailAddress() {
		return userInformations.emailAddress;
	}

	public boolean isLocked() {
		return isLocked;
	}

	private void lock() {
		isLocked = true;
	}

	public void confirmEmailAddress(String emailToConfirm) {
		if (!emailToConfirm.equals(getEmailAddress())) {
			throw new TryingToConfirmTheWrongEmailAddressException(emailToConfirm, getEmailAddress());
		} else if (isLocked) {
			isLocked = false;
		} else {
			throw new AlreadyConfirmedEmailAddressException();
		}
	}

}
