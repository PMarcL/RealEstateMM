package org.RealEstateMM.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.UserAccount;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {

	private final String PSEUDO = "bob32";
	private final String PASSWORD = "12345";
	private final String OTHER_PSEUDO = "algo133";
	private final String NAME_DUMMY = "Bobby";
	private final Email EMAIL_DUMMY = mock(Email.class);
	private final PhoneNumber PHONE_NUMBER_DUMMY = mock(PhoneNumber.class);

	private InMemoryUserRepository repository;
	private UserAccount user;

	@Before
	public void setup() {
		repository = new InMemoryUserRepository();
		user = new UserAccount(PSEUDO, PASSWORD, NAME_DUMMY, EMAIL_DUMMY, PHONE_NUMBER_DUMMY);
	}

	@Test
	public void containsInitiallyNoUser() {
		assertEquals(0, repository.getSize());
	}

	@Test
	public void givenAnEmptyRepositoryWhenAddingAUserThenShouldContainsOneUser() {
		repository.addUser(user);
		assertEquals(1, repository.getSize());
	}

	@Test
	public void givenNotEmptyRepositoryWhenAddNewUserThenShoudlContainsOneMoreUser() {
		repository.addUser(user);
		repository.addUser(mock(UserAccount.class));
		assertEquals(2, repository.getSize());
	}

	@Test(expected = PseudonymAlreadyUsedException.class)
	public void givenNotEmptyRepositoryWhenAddingUserWithSamePseudoAsPersistedUserThenShouldThrowException() {
		repository.addUser(user);
		repository.addUser(user);
	}

	@Test
	public void givenNotEmptyRepositoryWhenGetUserWithExistingPseudonymShouldReturnUserWithSamePseudonym() {
		repository.addUser(user);
		Optional<UserAccount> returnedUser = repository.getUserWithPseudonym(user.pseudonym);
		assertEquals(user, returnedUser.get());
	}

	@Test
	public void givenNotEmptryRepositoryWhenGetUserWithNonExistingPseudonymShouldReturnEmptyResult() {
		repository.addUser(user);
		Optional<UserAccount> returnedUser = repository.getUserWithPseudonym(OTHER_PSEUDO);
		assertFalse(returnedUser.isPresent());
	}
}
