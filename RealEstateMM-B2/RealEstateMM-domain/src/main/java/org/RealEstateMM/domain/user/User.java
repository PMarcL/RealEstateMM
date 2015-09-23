package org.RealEstateMM.domain.user;

public class User {

	public final String pseudonym;
	public final String password;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String phoneNumber;

	public User(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	// TODO
	// public boolean isEqual(User user) {
	// if (user == null) {
	// return false;
	// }
	//
	// boolean areEquals = this.email.equals(user.email);
	// areEquals &= this.firstName.equals(user.firstName);
	// areEquals &= this.lastName.equals(user.lastName);
	// areEquals &= this.phoneNumber.equals(user.phoneNumber);
	// areEquals &= this.pseudonym.equals(user.pseudonym);
	// return areEquals;
	// }

}
