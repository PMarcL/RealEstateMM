package org.RealEstateMM.persistence;

import static org.junit.Assert.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {
	private final String PSEUDONYM = "bobby194";

	private InMemoryUserRepositoryFake repository;

	@Before
	public void setup() {
		repository = new InMemoryUserRepositoryFake();
	}

	@Test
	public void isInitiallyEmpty() {
		assertTrue(repository.isEmpty());
	}

	@Test
	public void givenEmptyRepositoryWhenAddUserShouldNotBeEmpty() {
		repository.add(aUser().build());
		assertFalse(repository.isEmpty());
	}

	@Test
	public void givenEmptyRepositoryWhenAddUserShouldContainsOneElement() {
		repository.add(aUser().build());
		assertEquals(1, repository.size());
	}

	@Test
	public void givenNotEmptyRepositoryWhenAddNotExistingUserShouldContainsOneMoreUser() {
		repository.add(aUser().withPseudonym(PSEUDONYM).build());
		repository.add(aUser().withPseudonym("anotherPseudonym").build());
		assertEquals(2, repository.size());
	}

	@Test
	public void givenNewUserWhenAddedToRepositoryShouldContainsUser() {
		repository.add(aUser().withPseudonym(PSEUDONYM).build());
		assertTrue(repository.contains(PSEUDONYM));
	}

	@Test
	public void givenNotExistingUserPseudonymWhenCheckingIfContainsShouldNotContains() {
		assertFalse(repository.contains(PSEUDONYM));
	}

	@Test
	public void canRetrieveAddedUserWithPseudonym() {
		User newUser = aUser().withPseudonym(PSEUDONYM).build();
		repository.add(newUser);
		assertSame(newUser, repository.getUserWithPseudonym(PSEUDONYM).get());
	}

	@Test
	public void givenNotAddedUserWhenRetrivingWithPseudonymShouldReturnEmptyResult() {
		Optional<User> returnedUser = repository.getUserWithPseudonym(PSEUDONYM);
		assertFalse(returnedUser.isPresent());
	}

	private class InMemoryUserRepositoryFake extends InMemoryUserRepository {

		public boolean contains(String pseudonym) {
			return super.contains(pseudonym);
		}

		public void add(User user) {
			super.add(user);
		}
	}

	private UserBuilder aUser() {
		return new UserBuilder();
	}

	private class UserBuilder {
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

		public UserBuilder() {
			email = EMAIL;
			password = PASSWORD;
			firstName = FIRSTNAME;
			lastName = LASTNAME;
			phoneNumber = PHONE_NUMBER;
			pseudonym = PSEUDO;
		}

		public UserBuilder withPseudonym(String pseudonym) {
			this.pseudonym = pseudonym;
			return this;
		}

		User build() {
			return new User(pseudonym, password, firstName, lastName, email, phoneNumber);
		}
	}
}
