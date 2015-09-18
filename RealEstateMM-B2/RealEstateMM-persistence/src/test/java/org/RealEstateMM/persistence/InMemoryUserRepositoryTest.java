package org.RealEstateMM.persistence;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserAccount;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {

	private final String PSEUDO = "bob32";

	private InMemoryUserRepository repo;
	private UserAccount user;

	@Before
	public void initialisation() {
		repo = new InMemoryUserRepository();
		user = new UserAccount(PSEUDO, PSEUDO, null, null);
	}

	@Test
	public void givenAnEmptyRepositoryWhenGetSizeThenItsSizeIsZero() {
		assertEquals(0, repo.getSize());
	}

	@Test
	public void givenAnEmptyRepositoryWhenAddingAUserThenItsSizeIsOne() {
		repo.addUser(user);
		assertEquals(1, repo.getSize());
	}

	@Test
	public void givenARepositoryContainingUserXWhenAddingUserXThenItsSizeIsNotChanged() {
		repo.addUser(user);
		int initialSize = repo.getSize();

		repo.addUser(user);

		assertEquals(initialSize, repo.getSize());
	}

	@Test
	public void givenARepositoryContainingUserYWhenAddingUserXThenItsSizeIncrements() {
		repo.addUser(user);
		int initialSize = repo.getSize();

		repo.addUser(mock(UserAccount.class));

		assertEquals(initialSize + 1, repo.getSize());
	}
}
