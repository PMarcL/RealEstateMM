package org.RealEstateMM.domain.user;

public class UserBuilder {

	private String pseudonym;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String uerTypeDescription;

	public UserBuilder() {
	}

	public UserBuilder(String pseudonym, String password, String firstName, String lastName, String email,
			String phoneNumber) {
		this.pseudonym = pseudonym;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public UserBuilder withPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
		return this;
	}

	public UserBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder withUserType(String type) {
		this.uerTypeDescription = type;
		return this;
	}

	public User build() {
		UserInformation userInfo = new UserInformation(pseudonym, password, firstName, lastName, email, phoneNumber);
		UserType userType = getUserTypeFromDescription(uerTypeDescription);
		return new User(userInfo, userType);
	}

	private UserType getUserTypeFromDescription(String type) {
		if (type.equals(UserTypeDescription.SELLER)) {
			return UserType.SELLER;
		} else if (type.equals(UserTypeDescription.BUYER)) {
			return UserType.BUYER;
		} else {
			return UserType.ADMIN;
		}
	}
}
