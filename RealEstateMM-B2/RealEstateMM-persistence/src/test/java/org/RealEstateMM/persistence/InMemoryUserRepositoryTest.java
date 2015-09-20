package org.RealEstateMM.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;
import org.junit.Before;
import org.junit.Test;

public class InMemoryUserRepositoryTest {

	private final String PSEUDO = "bob32";
	private final String PASSWORD = "12345";
	private final String OTHER_PSEUDO = "algo133";
	private final Name NAME_DUMMY = new Name("Robert", "Fross");
	private final Email EMAIL_DUMMY = mock(Email.class);
	private final PhoneNumber PHONE_NUMBER_DUMMY = mock(PhoneNumber.class);

	private InMemoryUserRepository repository;
	private User user;

	@Before
	public void setup() {
		repository = new InMemoryUserRepository();
		user = new User(PSEUDO, PASSWORD, NAME_DUMMY, EMAIL_DUMMY, PHONE_NUMBER_DUMMY);
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
		repository.addUser(mock(User.class));
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
		Optional<User> returnedUser = repository.getUserWithPseudonym(user.pseudonym);
		assertEquals(user, returnedUser.get());
	}

	@Test
	public void givenNotEmptryRepositoryWhenGetUserWithNonExistingPseudonymShouldReturnEmptyResult() {
		repository.addUser(user);
		Optional<User> returnedUser = repository.getUserWithPseudonym(OTHER_PSEUDO);
		assertFalse(returnedUser.isPresent());
	}
}
