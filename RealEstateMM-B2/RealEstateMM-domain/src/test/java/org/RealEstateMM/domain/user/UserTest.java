package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.builders.UserBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private User user;
	private User otherUser;

	@Before
	public void initialisation() {
		user = aDefaultUserBuilder().build();
	}

	@Test
	public void givenTwoIdenticalUserInformationsWhenComparingThenShouldReturnsTrue() {
		otherUser = aDefaultUserBuilder().build();
		assertEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentEmailWhenComparingShouldReturnFalse() {
		final Email ANOTHER_EMAIL = new Email("emailTest@gmail.com");
		otherUser = aDefaultUserBuilder().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentNameWhenComparingShouldReturnFalse() {
		final Name ANOTHER_NAME = new Name("Bobby", "Dick");
		otherUser = aDefaultUserBuilder().withName(ANOTHER_NAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final PhoneNumber ANOTHER_PHONE_NUMBER = new PhoneNumber("(418)356-1234");
		otherUser = aDefaultUserBuilder().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = aDefaultUserBuilder().withPseudonym(ANOTHER_PSEUDO).build();
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

	private UserBuilder aDefaultUserBuilder() {
		String pseudo = "JohnD90";
		Name name = new Name("John", "Doe");
		Email email = new Email("example@hotmail.com");
		PhoneNumber phoneNumber = new PhoneNumber("(819) 418-5739");

		return new UserBuilder(pseudo, name, email, phoneNumber);
	}
}
