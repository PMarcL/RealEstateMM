package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserInformationsTest {

	private final String EMAIL = "example@hotmail.com";
	private final String NAME = "John Doe";
	private final String PHONENUMBER = "(819) 418-5739";
	private final String PSEUDO = "JohnD90";

	private UserInformations user;
	private UserInformations userToCompare;

	@Before
	public void initialisation() {
		userToCompare = new UserInformations();
		user = new UserInformations();
		user.email = EMAIL;
		user.name = NAME;
		user.phoneNumber = PHONENUMBER;
		user.pseudonym = PSEUDO;
	}

	@Test
	public void givenTwoIdenticalUserInformationsWhenComparingThenReturnsTrue() {
		userToCompare.email = EMAIL;
		userToCompare.name = NAME;
		userToCompare.phoneNumber = PHONENUMBER;
		userToCompare.pseudonym = PSEUDO;

		assertEquals(user, userToCompare);
	}

	@Test
	public void givenTwoDifferentUserInformationsWhenComparingThenReturnsFalse() {
		userToCompare.email = "email@gmail.com";
		userToCompare.name = "Bob Doe";
		userToCompare.phoneNumber = "(783) 378-3424";
		userToCompare.pseudonym = "Bob32";

		assertNotEquals(user, userToCompare);
	}

	@Test
	public void givenANullObjectWhenComparingThenReturnsFalse() {
		assertNotEquals(user, null);
	}
}
