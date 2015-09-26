package org.RealEstateMM.domain.helpers;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;

public class UserBuilder {
	public static final String DEFAULT_PSEUDO = "JohnD90";
	public static final String DEFAULT_EMAIL = "example@hotmail.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Doe";
	public static final String DEFAULT_PHONE_NUMBER = "(819) 418-5739";
	public static final String DEFAULT_PASSWORD = "JD1234";
	public static final String DEFAULT_USER_TYPE_DESC = "seller";

	private User user;

	public UserBuilder() {
		user = mock(User.class);

		withPseudonym(DEFAULT_PSEUDO);
		withFirstName(DEFAULT_FIRST_NAME);
		withLastName(DEFAULT_LAST_NAME);
		withEmail(DEFAULT_EMAIL);
		withPhoneNumber(DEFAULT_PHONE_NUMBER);
		withPassword(DEFAULT_PASSWORD);
		withUserType(DEFAULT_USER_TYPE_DESC);
	}

	public UserBuilder withPseudonym(String pseudonym) {
		given(user.getPseudonym()).willReturn(pseudonym);
		return this;
	}

	public UserBuilder withPhoneNumber(String phoneNumber) {
		given(user.getPhoneNumber()).willReturn(phoneNumber);
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		given(user.getFirstName()).willReturn(firstName);
		return this;
	}

	public UserBuilder withLastName(String lastName) {
		given(user.getLastName()).willReturn(lastName);
		return this;
	}

	public UserBuilder withEmail(String email) {
		given(user.getEmail()).willReturn(email);
		return this;
	}

	public UserBuilder withPassword(String password) {
		given(user.getPassword()).willReturn(password);
		return this;
	}

	public UserBuilder withUserType(String type) {
		given(user.getUserTypeDescription()).willReturn(type);
		return this;
	}

	public User build() {
		return user;
	}
}
