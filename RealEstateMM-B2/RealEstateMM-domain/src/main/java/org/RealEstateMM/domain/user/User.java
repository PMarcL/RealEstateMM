package org.RealEstateMM.domain.user;

public class User {

	private UserInformation userInformation;

	public User(String pseudonym, String password, String firstName, String lastName, String email, String phoneNumber) {
		this.userInformation = new UserInformation(pseudonym, password, firstName, lastName, email, phoneNumber);
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

}
