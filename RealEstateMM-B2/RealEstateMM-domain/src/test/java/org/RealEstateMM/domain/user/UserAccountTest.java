package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserAccountTest {
	private UserAccount user;
	private UserAccount otherUser;

	@Before
	public void initialisation() {
		user = aUser().build();
	}

	@Test
	public void givenTwoIdenticalUserInformationsWhenComparingThenShouldReturnsTrue() {
		otherUser = aUser().build();
		assertEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentEmailWhenComparingShouldReturnFalse() {
		final String ANOTHER_EMAIL = "emailTest@gmail.com";
		otherUser = aUser().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentNameWhenComparingShouldReturnFalse() {
		final String ANOTHER_NAME = "Bobby Dick";
		otherUser = aUser().withName(ANOTHER_NAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final String ANOTHER_PHONE_NUMBER = "(418)356-1234";
		otherUser = aUser().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = aUser().withPseudonym(ANOTHER_PSEUDO).build();
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

	private UserInformationsBuilder aUser() {
		return new UserInformationsBuilder();
	}

	private class UserInformationsBuilder {
		private final String EMAIL = "example@hotmail.com";
		private final String NAME = "John Doe";
		private final String PHONE_NUMBER = "(819) 418-5739";
		private final String PSEUDO = "JohnD90";

		private String email;
		private String name;
		private String phoneNumber;
		private String pseudonym;

		public UserInformationsBuilder() {
			email = EMAIL;
			name = NAME;
			phoneNumber = PHONE_NUMBER;
			pseudonym = PSEUDO;
		}

		public UserInformationsBuilder withPseudonym(String pseudonym) {
			this.pseudonym = pseudonym;
			return this;
		}

		public UserInformationsBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserInformationsBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public UserInformationsBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		UserAccount build() {
			return new UserAccount(pseudonym, name, new Email(email), new PhoneNumber(phoneNumber));
		}
	}
}
