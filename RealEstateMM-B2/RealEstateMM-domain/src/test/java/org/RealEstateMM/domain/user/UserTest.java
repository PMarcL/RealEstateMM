package org.RealEstateMM.domain.user;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserRole.RoleDescription;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private final String ANOTHER_PASSWORD = "234jd";
	private final String PSEUDONYM = "John90";
	private final String FIRSTNAME = "John";
	private final String LASTNAME = "Doe";
	private final String EMAIL_ADDRESS = "john@email.com";
	private final String PHONENUMBER = "819 819-3904";
	private final String PASSWORD = "jd1234";

	private User user;
	private UserInformations userInformations;
	private UserRole role;

	@Before
	public void setup() {
		userInformations = new UserInformations(PSEUDONYM, PASSWORD, FIRSTNAME, LASTNAME, EMAIL_ADDRESS, PHONENUMBER);
		role = mock(UserRole.class);
		user = new User(userInformations, role);
	}

	@Test
	public void givenAUserWhenGettingUserPseudonymThenReturnsHisPseudonym() {
		assertEquals(PSEUDONYM, user.getPseudonym());
	}

	@Test
	public void givenAUserWhenGettingUserInformationsThenReturnsHisInformations() {
		assertTrue(userInformations.isEquals(user.getUserInformations()));
	}

	@Test
	public void givenAUserWhenCheckingPasswordCompatibilityWithHisPasswordThenReturnsTrue() {
		assertTrue(user.hasPassword(PASSWORD));
	}

	@Test
	public void givenAUserWhenCheckingPasswordCompatibilityWithAnotherPasswordThenReturnsFalse() {
		assertFalse(user.hasPassword(ANOTHER_PASSWORD));
	}

	@Test
	public void givenAUserWhenGettingRoleDescriptionThenReturnDescriptionFromRole() {
		given(role.getRoleDescription()).willReturn(RoleDescription.SELLER);
		assertEquals(RoleDescription.SELLER, user.getRoleDescription());
	}

	@Test
	public void whenNewUserThenTheUserIsLocked() {
		assertTrue(user.isLocked());
	}

	@Test
	public void givenLockedUserWhenUnlockThenShouldNotBeLocked() {
		user.unlock();
		assertFalse(user.isLocked());
	}

	@Test
	public void givenUnlockedUserWhenLockThenShouldBeLocked() {
		user.unlock();
		user.lock();
		assertTrue(user.isLocked());
	}

	@Test
	public void givenEmailAddressEqualsUserEmailAddressWhenVerifyingIfUserHasEmailAddressShouldReturnTrue() {
		assertTrue(user.hasEmailAddress(EMAIL_ADDRESS));
	}

	@Test
	public void givenEmailAddressDifferentFromUserEmailAddressWhenVerifyingIfUserHasEmailAddressShouldReturnFalse() {
		assertFalse(user.hasEmailAddress("anotherEmailAddress@gmail.com"));
	}

	@Test
	public void givenUserInfosWhenUpdateUserInformationsShouldReplaceExistingUserInformations() {
		UserInformations newInfos = new UserInformations("anotherPseudonym", "anotherPassword", "anotherFirstName",
				"anotherLastName", "anotherEmail", "anotherPhoneNb");

		user.updateUserInformations(newInfos);

		assertEquals(newInfos, user.getUserInformations());
	}
}
