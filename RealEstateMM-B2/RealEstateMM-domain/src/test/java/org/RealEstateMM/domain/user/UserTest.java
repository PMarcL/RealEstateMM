package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

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
		assertTrue(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentEmailWhenComparingShouldReturnFalse() {
		final String ANOTHER_EMAIL = "emailTest@gmail.com";
		otherUser = aUser().withEmail(ANOTHER_EMAIL).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentFirstNameWhenComparingShouldReturnFalse() {
		final String ANOTHER_FIRSTNAME = "Bobby";
		otherUser = aUser().withFirstName(ANOTHER_FIRSTNAME).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentLastNameWhenComparingShouldReturnFalse() {
		final String ANOTHER_LASTNAME = "Dick";
		otherUser = aUser().withLastName(ANOTHER_LASTNAME).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final String ANOTHER_PHONE_NUMBER = "(418)356-1234";
		otherUser = aUser().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = aUser().withPseudonym(ANOTHER_PSEUDO).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenTwoUsersWithDifferentPasswordWhenComparingShouldReturnFalse() {
		final String ANOTHER_PASSWORD = "j129L";
		otherUser = aUser().withPassword(ANOTHER_PASSWORD).build();
		assertFalse(user.isEquals(otherUser));
	}

	@Test
	public void givenANullObjectWhenComparingThenShouldReturnsFalse() {
		assertFalse(user.isEquals(null));
	}

	private UserAccountBuilder aUser() {
		return new UserAccountBuilder();
	}

	private class UserAccountBuilder {
		private final String EMAIL = "example@hotmail.com";
		private final String PASSWORD = "12345";
		private final String FIRSTNAME = "John";
		private final String LASTNAME = "Doe";
		private final String PHONE_NUMBER = "(819) 418-5739";
		private final String PSEUDO = "JohnD90";

		private String email;
		private String firstName;
		private String lastName;
		private String phoneNumber;
		private String pseudonym;
		private String password;

		public UserAccountBuilder() {
			email = EMAIL;
			password = PASSWORD;
			firstName = FIRSTNAME;
			lastName = LASTNAME;
			phoneNumber = PHONE_NUMBER;
			pseudonym = PSEUDO;
		}

		public UserAccountBuilder withPseudonym(String pseudonym) {
			this.pseudonym = pseudonym;
			return this;
		}

		public UserAccountBuilder withPassword(String password) {
			this.password = password;
			return this;
		}

		public UserAccountBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserAccountBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public UserAccountBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public UserAccountBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		User build() {
			return new User(pseudonym, password, firstName, lastName, email, phoneNumber);
		}
	}

}
