package org.RealEstateMM.domain.user;

public class UserType {
	public final static String SELLER = "seller";
	public final static String BUYER = "buyer";
	public final static String ADMIN = "admin";

	public final String userTypeDescription;

	public UserType(String userType) {
		userTypeDescription = userType;
	}

}
