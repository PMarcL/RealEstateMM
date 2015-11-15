package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Before;
import org.junit.Test;

public class UserAuthorizationsTest {
	private final String PSEUDONYM = "bob134";
	private final AccessLevel AUTHORIZED_ACCESS_LEVEL = AccessLevel.ADMIN;
	private final AccessLevel REFUSED_ACCESS_LEVEL = AccessLevel.BUYER;

	private User user;
	private UserRepository userRepository;
	private UserAuthorizations authorizations;

	@Before
	public void setup() {
		user = mock(User.class);
		given(user.isAuthorized(REFUSED_ACCESS_LEVEL)).willReturn(false);
		given(user.isAuthorized(AUTHORIZED_ACCESS_LEVEL)).willReturn(true);
		userRepository = mock(UserRepository.class);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(user);
		authorizations = new UserAuthorizations(userRepository);
	}

	@Test
	public void whenCheckingIfUserIsAuthorizedThenShouldCheckIfUserWhithPseudonymIsAuthorizedForEachAccessLevel() {
		authorizations.isUserAuthorized(PSEUDONYM, REFUSED_ACCESS_LEVEL, REFUSED_ACCESS_LEVEL);
		verify(user, times(2)).isAuthorized(REFUSED_ACCESS_LEVEL);
	}

	@Test
	public void givenUserHasAuthorizationsForOneAccessLevelWhenCheckingIfUserIsAuthorizedThenShouldReturnTrue() {
		assertTrue(authorizations.isUserAuthorized(PSEUDONYM, AUTHORIZED_ACCESS_LEVEL));
	}

	@Test
	public void givenUserDoesNotHaveAuthorizationsForAnyAccessLevelWhenChekingIfUserIsAuthorizedThenShouldReturnFalse() {
		assertFalse(authorizations.isUserAuthorized(PSEUDONYM, REFUSED_ACCESS_LEVEL));
	}

}
