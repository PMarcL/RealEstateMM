package org.RealEstateMM.persistence.memory;

import static org.junit.Assert.*;

import java.util.Optional;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.persistence.memory.InMemoryUserRepository;
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
		repository.add(new DefaultUserBuilder().build());
		assertFalse(repository.isEmpty());
	}

	@Test
	public void givenEmptyRepositoryWhenAddUserShouldContainsOneElement() {
		repository.add(new DefaultUserBuilder().build());
		assertEquals(1, repository.size());
	}

	@Test
	public void givenNotEmptyRepositoryWhenAddNotExistingUserShouldContainsOneMoreUser() {
		repository.add(new DefaultUserBuilder().withPseudonym(PSEUDONYM).build());
		repository.add(new DefaultUserBuilder().withPseudonym("anotherPseudonym").build());
		assertEquals(2, repository.size());
	}

	@Test
	public void givenNewUserWhenAddedToRepositoryShouldContainsUser() {
		repository.add(new DefaultUserBuilder().withPseudonym(PSEUDONYM).build());
		assertTrue(repository.contains(PSEUDONYM));
	}

	@Test
	public void givenNotExistingUserPseudonymWhenCheckingIfContainsShouldNotContains() {
		assertFalse(repository.contains(PSEUDONYM));
	}

	@Test
	public void canRetrieveAddedUserWithPseudonym() {
		User newUser = new DefaultUserBuilder().withPseudonym(PSEUDONYM).build();
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
}
