package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.exceptions.UserWithPseudonymAlreadyStoredException;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryTest {
	private final String PSEUDONYM = "bboy134";

	private UserRepositoryFake repository;
	private User user;
	private User foundUser;

	@Before
	public void setup() {
		repository = new UserRepositoryFake();
		user = new UserBuilder().withPseudonym(PSEUDONYM).build();
		foundUser = mock(User.class);
	}

	@Test
	public void givenANonExistingUserWhenAddUserShouldAddToRepository() {
		repository.addUser(user);
		repository.verifyAddCalledWithUser(user);
	}

	@Test(expected = UserWithPseudonymAlreadyStoredException.class)
	public void givenAnExistingUserWhenAddUserWithSamePseudonymShouldThrowException() {
		repository.addExistingUser(user);
		repository.addUser(user);
	}

	@Test
	public void givenAnExistingUserWhenReplaceUserShouldRemoveUserAndThenAddNewUser() {
		repository.addExistingUser(user);

		repository.replaceUser(user);

		repository.verifyRemoveCalledBeforeAdd(user);
		repository.verifyRemoveCalledWithPseudonym(PSEUDONYM);
	}

	@Test
	public void givenAnExistingUserWhenGetUserWithPseudonymShouldFindUserWithPseudonym() {
		repository.addExistingUser(user);
		User result = repository.getUserWithPseudonym(PSEUDONYM);
		assertSame(foundUser, result);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenAnUnexistingUserWhenGetUserWithPseudonymShouldThrowException() {
		repository.getUserWithPseudonym(PSEUDONYM);
	}

	private class UserRepositoryFake extends UserRepository {
		private boolean addCalled;
		private boolean removeCalledBeforeAdd;
		private String removeCalledPseudonym;
		private User addedUser;
		private String existingUserPseudo;

		public UserRepositoryFake() {
			addCalled = false;
			removeCalledBeforeAdd = false;
			removeCalledPseudonym = "";
		}

		public void verifyRemoveCalledWithPseudonym(String expectedPseudonym) {
			assertEquals(expectedPseudonym, removeCalledPseudonym);
		}

		public void verifyRemoveCalledBeforeAdd(User user) {
			assertTrue(removeCalledBeforeAdd);
		}

		public void addExistingUser(User newUser) {
			existingUserPseudo = newUser.getPseudonym();
		}

		@Override
		protected boolean contains(String pseudonym) {
			if (existingUserPseudo == null) {
				return false;
			}

			return (existingUserPseudo.equals(pseudonym));
		}

		@Override
		protected void add(User user) {
			addCalled = true;
			addedUser = user;
		}

		public void verifyAddCalledWithUser(User user) {
			assertTrue(addCalled);
			assertSame(user, addedUser);
		}

		@Override
		protected void removeUserWithPseudonym(String pseudonym) {
			removeCalledBeforeAdd = (!addCalled);
			removeCalledPseudonym = pseudonym;
		}

		@Override
		protected User findUserWithPseudonym(String pseudonym) {
			return foundUser;
		}

		@Override
		public List<User> getAll() {
			return null;
		}
	}
}
