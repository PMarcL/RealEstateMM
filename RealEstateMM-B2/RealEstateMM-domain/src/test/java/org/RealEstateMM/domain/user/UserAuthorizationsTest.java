package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Before;
import org.junit.Test;

public class UserAuthorizationsTest {
	private final String PSEUDONYM = "bob134";
	private final AccessLevel ACCESS_LEVEL = AccessLevel.ADMIN;

	private User user;
	private UserRepository userRepository;
	private UserAuthorizations authorizations;

	@Before
	public void setup() {
		user = mock(User.class);
		userRepository = mock(UserRepository.class);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.of(user));
		authorizations = new UserAuthorizations(userRepository);
	}

	@Test
	public void whenCheckingIfUserIsAuthorizedThenShouldCheckIfUserWhithPseudonymIsAuthorized() {
		authorizations.isUserAuthorized(PSEUDONYM, ACCESS_LEVEL);
		verify(user).isAuthorized(ACCESS_LEVEL);
	}

	@Test
	public void givenUserHasAuthorizationsWhenCheckingIfUserIsAuthorizedThenShouldReturnTrue() {
		given(user.isAuthorized(ACCESS_LEVEL)).willReturn(true);
		assertTrue(authorizations.isUserAuthorized(PSEUDONYM, ACCESS_LEVEL));
	}

	@Test
	public void givenUserDoesNotHaveAuthorizationsWhenChekingIfUserIsAuthorizedThenShouldReturnFalse() {
		given(user.isAuthorized(ACCESS_LEVEL)).willReturn(false);
		assertFalse(authorizations.isUserAuthorized(PSEUDONYM, ACCESS_LEVEL));
	}

	@Test
	public void givenUserNotPresentInRepositoryWhenCheckingIfUserIsAuthorizedThenShouldReturnFalse() {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.empty());
		assertFalse(authorizations.isUserAuthorized(PSEUDONYM, ACCESS_LEVEL));
	}

}
