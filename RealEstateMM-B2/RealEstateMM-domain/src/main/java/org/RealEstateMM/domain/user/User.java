package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeDescription;

public class User {

	private UserInformation userInformation;
	private UserType userType;

	public User(UserInformation userInfo, UserType type) {
		this.userInformation = userInfo;
		this.userType = type;
	}

	public String getPhoneNumber() {
		return userInformation.phoneNumber;
	}

	public String getEmail() {
		return userInformation.email;
	}

	public String getLastName() {
		return userInformation.lastName;
	}

	public String getFirstName() {
		return userInformation.firstName;
	}

	public String getPassword() {
		return userInformation.password;
	}

	public String getPseudonym() {
		return userInformation.pseudonym;
	}

	public boolean hasPassword(String password) {
		return userInformation.password.equals(password);
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
