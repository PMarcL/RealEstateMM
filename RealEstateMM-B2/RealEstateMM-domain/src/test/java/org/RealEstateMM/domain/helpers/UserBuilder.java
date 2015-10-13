package org.RealEstateMM.domain.helpers;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;

public class UserBuilder {
	public static final String DEFAULT_PSEUDO = "JohnD90";
	public static final String DEFAULT_EMAIL = "example@hotmail.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Doe";
	public static final String DEFAULT_PHONE_NUMBER = "(819) 418-5739";
	public static final String DEFAULT_PASSWORD = "JD1234";
	public static final String DEFAULT_USER_TYPE_DESC = "seller";
	public static final boolean DEFAULT_LOCK_STATE = true;

	private User user;
	private UserInformations userInfo;

	public UserBuilder() {
		user = mock(User.class);
		userInfo = new UserInformations(DEFAULT_PSEUDO, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME,
				DEFAULT_EMAIL, DEFAULT_PHONE_NUMBER);

		withPseudonym(DEFAULT_PSEUDO);
		withUserType(DEFAULT_USER_TYPE_DESC);
		withLockState(DEFAULT_LOCK_STATE);

		given(user.getUserInformations()).willReturn(userInfo);
	}

	public UserBuilder withLockState(boolean lockState) {
		given(user.isLocked()).willReturn(lockState);
		return this;
	}

	public UserBuilder withPseudonym(String pseudonym) {
		given(user.getPseudonym()).willReturn(pseudonym);
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
