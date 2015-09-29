package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeDescription;

public class User {

	private UserInformations userInformations;
	private UserType userType;

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
		if (userType == UserType.BUYER) {
			return UserTypeDescription.BUYER;
		} else if (userType == UserType.SELLER) {
			return UserTypeDescription.SELLER;
		} else {
			return UserTypeDescription.ADMIN;
		}
	}

}
