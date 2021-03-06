package org.RealEstateMM.domain.helpers;

import static org.mockito.BDDMockito.*;

import java.util.Date;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserBuilder {
	public static final String DEFAULT_PSEUDONYM = "JohnD90";
	public static final String DEFAULT_EMAIL_ADDRESS = "example@hotmail.com";
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Doe";
	public static final String DEFAULT_PHONE_NUMBER = "(819) 418-5739";
	public static final String DEFAULT_PASSWORD = "JD1234";
	public static final boolean DEFAULT_LOCK_STATE = true;
	public static final AccessLevel DEFAULT_USER_ROLE = AccessLevel.SELLER;
	public static final Date DEFAULT_LAST_LOGIN_DATE = new Date();

	private User user;
	private String pseudonym;

	public UserBuilder() {
		user = mock(User.class);

		pseudonym = DEFAULT_PSEUDONYM;

		withPseudonym(DEFAULT_PSEUDONYM);
		withLockState(DEFAULT_LOCK_STATE);
		withRoleDescription(DEFAULT_USER_ROLE);
		withLastLoginDate(DEFAULT_LAST_LOGIN_DATE);
	}

	public UserBuilder withRoleDescription(AccessLevel role) {
		given(user.getRoleDescription()).willReturn(role);
		return this;
	}

	public UserBuilder withLastLoginDate(Date lastLoginDate) {
		given(user.getLastLoginDate()).willReturn(lastLoginDate);
		return this;
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

	public User build() {
		UserInformations userInfos = new UserInformations(pseudonym, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
				DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, DEFAULT_PHONE_NUMBER);
		given(user.getUserInformations()).willReturn(userInfos);

		return user;
	}
}
