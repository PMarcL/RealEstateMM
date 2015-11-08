package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Test;

public class UserRoleTest {
	private final AccessLevel ACCESS_LEVEL = AccessLevel.ADMIN;

	@Test
	public void givenAnAccessLevelIsSameAsInstanceRoleDescriptionWhenCheckIfIsAuthorizedThenShouldReturnTrue() {
		UserRole role = new UserRoleTestImpl();
		assertTrue(role.isAuthorized(ACCESS_LEVEL));
	}

	@Test
	public void givenAnAccessLevelDifferentFromInstanceRoleDescriptionWhenCheckIfIsAuthorizedThenShouldReturnFalse() {
		UserRole role = new UserRoleTestImpl();
		assertFalse(role.isAuthorized(AccessLevel.BUYER));
	}

	private class UserRoleTestImpl extends UserRole {

		public UserRoleTestImpl() {
			super(ACCESS_LEVEL);
		}

	}
}
