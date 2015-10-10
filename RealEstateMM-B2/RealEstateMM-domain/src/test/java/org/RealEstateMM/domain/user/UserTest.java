package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private final String ANOTHER_PASSWORD = "234jd";
	private final String PSEUDONYM = "John90";
	private final String FIRSTNAME = "John";
	private final String LASTNAME = "Doe";
	private final String EMAIL = "john@email.com";
	private final String PHONENUMBER = "819 819-3904";
	private final String PASSWORD = "jd1234";
	private final String USERTYPE_DESCRIPTION = UserType.SELLER;

	private User user;
	private UserInformations userInformations;
	private UserType userType;

	@Before
	public void setup() {
		userInformations = new UserInformations(PSEUDONYM, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER);
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
}
