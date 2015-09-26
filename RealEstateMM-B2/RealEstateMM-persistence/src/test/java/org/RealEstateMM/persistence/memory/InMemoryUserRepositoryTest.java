package org.RealEstateMM.persistence.memory;

import static org.junit.Assert.*;

import java.util.Optional;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {
	private final String PSEUDONYM = "bobby194";

	private InMemoryUserRepository repository;

	@Before
	public void setup() {
		repository = new InMemoryUserRepository();
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

	private UserBuilder aUser() {
		return new UserBuilder();
	}
}
