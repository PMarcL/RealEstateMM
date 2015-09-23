package org.RealEstateMM.domain.user;

public class UserInformation {

	public final String firstName;
	public final String lastName;
	public final String email;
	public final String phoneNumber;

	public UserInformation(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

}
