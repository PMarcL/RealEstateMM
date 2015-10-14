package org.RealEstateMM.domain.helpers;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;

public class UserBuilder {
	public static final String DEFAULT_PSEUDONYM = "JohnD90";
	public static final String DEFAULT_EMAIL_ADDRESS = "example@hotmail.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Doe";
	public static final String DEFAULT_PHONE_NUMBER = "(819) 418-5739";
	public static final String DEFAULT_PASSWORD = "JD1234";
	public static final String DEFAULT_USER_TYPE_DESC = "seller";
	public static final boolean DEFAULT_LOCK_STATE = true;

	private User user;
	private String pseudonym;

	public UserBuilder() {
		user = mock(User.class);

		pseudonym = DEFAULT_PSEUDONYM;

		withPseudonym(DEFAULT_PSEUDONYM);
		withUserType(DEFAULT_USER_TYPE_DESC);
		withLockState(DEFAULT_LOCK_STATE);
	}

	public UserBuilder withLockState(boolean lockState) {
		given(user.isLocked()).willReturn(lockState);
		return this;
	}

	public UserBuilder withPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
		given(user.getPseudonym()).willReturn(pseudonym);
		return this;
	}

	public UserBuilder withUserType(String type) {
		given(user.getUserTypeDescription()).willReturn(type);
		return this;
	}

	public User build() {
		UserInformations userInfos = new UserInformations(pseudonym, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
				DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, DEFAULT_PHONE_NUMBER);
		given(user.getUserInformations()).willReturn(userInfos);

		return user;
	}
}
