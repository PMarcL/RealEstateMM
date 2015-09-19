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
		user = aUserBuilder().build();
	}

	@Test
	public void givenTwoIdenticalUserInformationsWhenComparingThenShouldReturnsTrue() {
		otherUser = aUserBuilder().build();
		assertEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentEmailWhenComparingShouldReturnFalse() {
		final Email ANOTHER_EMAIL = new Email("emailTest@gmail.com");
		otherUser = aUserBuilder().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentNameWhenComparingShouldReturnFalse() {
		final Name ANOTHER_NAME = new Name("Bobby", "Dick");
		otherUser = aUserBuilder().withName(ANOTHER_NAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final PhoneNumber ANOTHER_PHONE_NUMBER = new PhoneNumber("(418)356-1234");
		otherUser = aUserBuilder().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = aUserBuilder().withPseudonym(ANOTHER_PSEUDO).build();
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

	private UserBuilder aUserBuilder() {
		String pseudo = "JohnD90";
		Name name = new Name("John", "Doe");
		Email email = new Email("example@hotmail.com");
		PhoneNumber phoneNumber = new PhoneNumber("(819) 418-5739");

		UserBuilder builder = new UserBuilder();
		builder.setDefaults(pseudo, name, email, phoneNumber);

		return builder;
	}
}
