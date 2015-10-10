package org.RealEstateMM.domain.user;

public class UserInformations {

	public final String pseudonym;
	public final String firstName;
	public final String lastName;
	public final String emailAddress;
	public final String phoneNumber;
	public final String password;

	public UserInformations(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = email;
		this.phoneNumber = phoneNumber;
	}

	public boolean isEquals(UserInformations userInformations) {
		boolean areEquals = true;
		areEquals &= pseudonym.equals(userInformations.pseudonym);
		areEquals &= password.equals(userInformations.password);
		areEquals &= firstName.equals(userInformations.firstName);
		areEquals &= lastName.equals(userInformations.lastName);
		areEquals &= emailAddress.equals(userInformations.emailAddress);
		areEquals &= phoneNumber.equals(userInformations.phoneNumber);
		return areEquals;
	}

}
