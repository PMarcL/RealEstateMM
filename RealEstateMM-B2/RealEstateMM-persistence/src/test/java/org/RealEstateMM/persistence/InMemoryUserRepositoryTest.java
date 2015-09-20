package org.RealEstateMM.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.RealEstateMM.domain.builders.DefaultUserBuilder;
import org.RealEstateMM.domain.models.user.User;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {

	private InMemoryUserRepository repo;
	private User user;

	@Before
	public void initialisation() {
		repo = new InMemoryUserRepository();
		user = new DefaultUserBuilder().build();
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

		repo.addUser(mock(User.class));

		assertEquals(initialSize + 1, repo.getSize());
	}
}
