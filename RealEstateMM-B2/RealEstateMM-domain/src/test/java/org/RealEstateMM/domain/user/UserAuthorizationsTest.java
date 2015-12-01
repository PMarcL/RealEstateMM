package org.RealEstateMM.domain.user;

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
	public void setup() throws Throwable {
		user = mock(User.class);
		given(user.isAuthorized(REFUSED_ACCESS_LEVEL)).willReturn(false);
		given(user.isAuthorized(AUTHORIZED_ACCESS_LEVEL)).willReturn(true);
		userRepository = mock(UserRepository.class);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(user);
		authorizations = new UserAuthorizations(userRepository);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserDoesNotHaveAnyAcceptedAccessLevelWhenValidatingUserAccessShouldThrowException()
			throws Throwable {
		authorizations.validateUserAuthorizations(PSEUDONYM, REFUSED_ACCESS_LEVEL, REFUSED_ACCESS_LEVEL);
	}

	@Test
	public void givenUserHasAnAcceptedUserAccessWhenValidatingUserAccessShouldDoNothing() throws Throwable {
		authorizations.validateUserAuthorizations(PSEUDONYM, REFUSED_ACCESS_LEVEL, AUTHORIZED_ACCESS_LEVEL);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserDoesNotExistWhenCheckingIfUserIsAuthorizedThenShouldThrowException() throws Throwable {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willThrow(new UserNotFoundException(PSEUDONYM));
		authorizations.validateUserAuthorizations(PSEUDONYM, AUTHORIZED_ACCESS_LEVEL);
	}

}
