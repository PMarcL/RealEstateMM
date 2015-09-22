package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private User user;
	private User otherUser;

	@Before
	public void initialisation() {
		user = new DefaultUserBuilder().build();
	}

	@Test
	public void givenTwoIdenticalUsersWhenComparingThenShouldReturnsTrue() {
		otherUser = new DefaultUserBuilder().build();
		assertTrue(user.isEqual(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentEmailWhenComparingShouldReturnFalse() {
		final String ANOTHER_EMAIL = "emailTest@gmail.com";
		otherUser = new DefaultUserBuilder().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentFirstNameWhenComparingShouldReturnFalse() {
		final String ANOTHER_FIRSTNAME = "Bobby";
		otherUser = new DefaultUserBuilder().withFirstName(ANOTHER_FIRSTNAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentLastNameWhenComparingShouldReturnFalse() {
		final String ANOTHER_LASTNAME = "Dick";
		otherUser = new DefaultUserBuilder().withLastName(ANOTHER_LASTNAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final String ANOTHER_PHONE_NUMBER = "(418)356-1234";
		otherUser = new DefaultUserBuilder().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = new DefaultUserBuilder().withPseudonym(ANOTHER_PSEUDO).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenANullObjectWhenComparingThenShouldReturnsFalse() {
		assertNotEquals(user, null);
	}

	@Test
	public void givenAnObjectOfADifferentTypeWhenComparingThenShouldReturnsFalse() {
		String objectOfAnotherType = "I am a string";
		assertNotEquals(user, objectOfAnotherType);
	}

}
