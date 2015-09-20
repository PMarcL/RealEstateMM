package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private User user;
	private User otherUser;

	@Before
	public void initialisation() {
		user = aUser().build();
	}

	@Test
	public void givenTwoIdenticalUsersWhenComparingThenShouldReturnsTrue() {
		otherUser = aUser().build();
		assertEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentEmailWhenComparingShouldReturnFalse() {
		final String ANOTHER_EMAIL = "emailTest@gmail.com";
		otherUser = aUser().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentNameWhenComparingShouldReturnFalse() {
		final Name ANOTHER_NAME = new Name("Bobby", "Dick");
		otherUser = aUser().withName(ANOTHER_NAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final String ANOTHER_PHONE_NUMBER = "(418)356-1234";
		otherUser = aUser().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = aUser().withPseudonym(ANOTHER_PSEUDO).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUsersWithDifferentPasswordWhenComparingShouldReturnFalse() {
		final String ANOTHER_PASSWORD = "potatoe123";
		otherUser = aUser().withPassword(ANOTHER_PASSWORD).build();
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

	@Test
	public void givenSamePasswordWhenCheckingPasswordShouldReturnTrue() {
		assertTrue(user.hasPassword(user.password));
	}

	@Test
	public void givenDifferentPasswordWhenCheckingPasswordShouldReturnFalse() {
		final String ANOTHER_PASSWORD = "password123";
		assertFalse(user.hasPassword(ANOTHER_PASSWORD));
	}

	private UserAccountBuilder aUser() {
		return new UserAccountBuilder();
	}

	private class UserAccountBuilder {
		private final String EMAIL = "example@hotmail.com";
		private final Name NAME = new Name("John", "Doe");
		private final String PASSWORD = "12345";
		private final String PHONE_NUMBER = "(819) 418-5739";
		private final String PSEUDO = "JohnD90";

		private String email;
		private Name name;
		private String phoneNumber;
		private String pseudonym;
		private String password;

		public UserAccountBuilder() {
			email = EMAIL;
			name = NAME;
			phoneNumber = PHONE_NUMBER;
			pseudonym = PSEUDO;
			password = PASSWORD;
		}

		public UserAccountBuilder withPassword(String password) {
			this.password = password;
			return this;
		}

		public UserAccountBuilder withPseudonym(String pseudonym) {
			this.pseudonym = pseudonym;
			return this;
		}

		public UserAccountBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserAccountBuilder withName(Name name) {
			this.name = name;
			return this;
		}

		public UserAccountBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		User build() {
			return new User(pseudonym, password, name, new Email(email), new PhoneNumber(phoneNumber));
		}
	}

}
