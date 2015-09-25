package org.RealEstateMM.domain.user;

public class UserInformation {

	public final String pseudonym;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String phoneNumber;
	public final String password;

	public UserInformation(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

}
