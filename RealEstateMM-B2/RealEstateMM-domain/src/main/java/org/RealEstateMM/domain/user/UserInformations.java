package org.RealEstateMM.domain.user;

public class UserInformations {

	public final String pseudonym;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String phoneNumber;
	public final String password;

	public UserInformations(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public boolean isEquals(UserInformations userInformations) {
		boolean areEquals = true;
		areEquals &= pseudonym.equals(userInformations.pseudonym);
		areEquals &= password.equals(userInformations.password);
		areEquals &= firstName.equals(userInformations.firstName);
		areEquals &= lastName.equals(userInformations.lastName);
		areEquals &= email.equals(userInformations.email);
		areEquals &= phoneNumber.equals(userInformations.phoneNumber);
		return areEquals;
	}

}
