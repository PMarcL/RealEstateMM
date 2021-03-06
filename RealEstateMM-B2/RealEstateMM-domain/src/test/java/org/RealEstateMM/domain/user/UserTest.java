package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Calendar;
import java.util.Date;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;
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
	private final AccessLevel ACCESS_LEVEL = AccessLevel.SELLER;

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
		given(role.getRoleDescription()).willReturn(AccessLevel.SELLER);
		assertEquals(AccessLevel.SELLER, user.getRoleDescription());
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

	@Test
	public void whenCheckingIfUserIsAuthorizedThenShouldAskUserRole() {
		given(role.isAuthorized(ACCESS_LEVEL)).willReturn(true);
		assertTrue(user.isAuthorized(ACCESS_LEVEL));
	}

	@Test
	public void givenTheCorrectPasswordWithAConfirmedUserWhenAuthenticateThenLogin() {
		User confirmedUser = aConfirmedUserWithPassword(PASSWORD);
		Calendar dateBeforeAuthentication = Calendar.getInstance();

		confirmedUser.authenticate(PASSWORD);

		Calendar dateOfLastLogin = Calendar.getInstance();
		dateOfLastLogin.setTime(confirmedUser.getLastLoginDate());
		assertTrue(
				dateBeforeAuthentication.before(dateOfLastLogin) || dateBeforeAuthentication.equals(dateOfLastLogin));
	}

	private User aConfirmedUserWithPassword(String password) {
		UserInformations userInfo = new UserInformations(null, password, null, null, null, null);
		User confirmedUser = new User(userInfo, new Buyer());
		confirmedUser.unlock();
		return confirmedUser;
	}

	@Test(expected = UnconfirmedEmailException.class)
	public void givenALockedUserWhenAuthenticateThrowUnconfirmedEmailException() {
		User unconfirmedUser = user;
		unconfirmedUser.authenticate(PASSWORD);
		assertNull(unconfirmedUser.getLastLoginDate());
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenAuthenticateThrowInvalidPasswordException() {
		String invalidPassword = "posdf33";
		User confirmedUser = aConfirmedUserWithPassword(PASSWORD);
		confirmedUser.authenticate(invalidPassword);
		assertNull(confirmedUser.getLastLoginDate());
	}

	@Test
	public void givenAUserWhoJustLoggedInWhenCheckingIfHasLoggedAfterYesterdayThenReturnTrue() {
		user.setLastLoginDate(new Date());

		Calendar yesterdayCalendar = Calendar.getInstance();
		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = yesterdayCalendar.getTime();
		boolean actual = user.hasLoggedLastAfter(yesterday);

		assertTrue(actual);
	}

	@Test
	public void givenAUserWhoJustLoggedInWhenCheckingIfHasLoggedAfterTomorrowThenReturnFalse() {
		user.setLastLoginDate(new Date());

		Calendar yesterdayCalendar = Calendar.getInstance();
		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, +1);
		Date yesterday = yesterdayCalendar.getTime();
		boolean actual = user.hasLoggedLastAfter(yesterday);

		assertFalse(actual);
	}
}
