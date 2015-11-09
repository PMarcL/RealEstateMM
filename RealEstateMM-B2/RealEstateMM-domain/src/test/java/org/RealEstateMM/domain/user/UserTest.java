package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.RealEstateMM.domain.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.domain.user.exceptions.UnconfirmedEmailException;
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
	private final String USERTYPE_DESCRIPTION = UserType.SELLER;

	private User user;
	private UserInformations userInformations;
	private UserType userType;

	@Before
	public void setup() {
		userInformations = new UserInformations(PSEUDONYM, PASSWORD, FIRSTNAME, LASTNAME, EMAIL_ADDRESS, PHONENUMBER);
		userType = new UserType(UserType.SELLER);
		user = new User(userInformations, userType);
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
	public void givenAUserWhenGettingHisUserTypeDescriptionThenReturnsTheCorrectDescription() {
		assertEquals(USERTYPE_DESCRIPTION, user.getUserTypeDescription());
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
	public void whenNewUserThenTheLastLoginDateIsNull() {
		assertNull(user.getLastLoginDate());
	}

	@Test
	public void givenTheCorrectPasswordWithAConfirmedUserWhenAuthenticateThenLogin() throws Exception {
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
		User confirmedUser = new User(userInfo, new UserType(UserType.BUYER));
		confirmedUser.unlock();
		return confirmedUser;
	}

	@Test(expected = UnconfirmedEmailException.class)
	public void givenALockedUserWhenAuthenticateThrowUnconfirmedEmailException() throws Exception {
		User unconfirmedUser = user;
		unconfirmedUser.authenticate(PASSWORD);
		assertNull(unconfirmedUser.getLastLoginDate());
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenAuthenticateThrowInvalidPasswordException() throws Exception {
		String invalidPassword = "posdf33";
		User confirmedUser = aConfirmedUserWithPassword(PASSWORD);
		confirmedUser.authenticate(invalidPassword);
		assertNull(confirmedUser.getLastLoginDate());
	}

}
