package org.RealEstateMM.domain.user.usertype;

public class UserTypeFactory {

	public UserType makeUserType(String userTypeDescription) {
		if (userTypeDescription.equals(UserTypeDescription.SELLER)) {
			return UserType.SELLER;
		} else if (userTypeDescription.equals(UserTypeDescription.BUYER)) {
			return UserType.BUYER;
		} else {
			return UserType.ADMIN;
		}
	}
}
