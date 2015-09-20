package org.RealEstateMM.services.dto;

public class UserInformations {

	public String pseudonym;
	public String firstName;
	public String lastName;
	public String email;
	public String phoneNumber;
	public String password;

	public UserInformations() {
	}

	public UserInformations(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

}
