package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryTest {

	private UserRepositoryFake repository;

	@Before
	public void setup() {
		repository = new UserRepositoryFake();
	}

	@Test
	public void givenANonExistingUserWhenAddUserShouldAddToRepository() {
		User newUser = mock(User.class);
		repository.addUser(newUser);
		repository.verifyAddCalledWithUser(newUser);
	}

	@Test(expected = UserWithPseudonymAlreadyStoredException.class)
	public void givenAnExistingUserWhenAddUserWithSamePseudonymShouldThrowException() {
		User newUser = new UserBuilder().build();
		repository.addExistingUser(newUser);

		repository.addUser(newUser);
	}

	private class UserRepositoryFake extends UserRepository {
		private boolean addCalled;
		private User addedUser;
		private String existingUserPseudo;

		public UserRepositoryFake() {
			addCalled = false;
		}

		public void addExistingUser(User newUser) {
			existingUserPseudo = newUser.getPseudonym();
		}

		@Override
		public Optional<User> getUserWithPseudonym(String pseudonym) {
			return null;
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
	}
}
