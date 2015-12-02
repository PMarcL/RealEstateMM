package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserAuthorizations {

	private UserRepository userRepository;

	public UserAuthorizations(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validateUserAuthorizations(String pseudonym, AccessLevel... accessLevels)
			throws ForbiddenAccessException {
		try {
			User user = userRepository.getUserWithPseudonym(pseudonym);
			verifyUserAccessLevel(user, accessLevels);
		} catch (UserNotFoundException e) {
			throw new ForbiddenAccessException();
		}

	}

	private void verifyUserAccessLevel(User user, AccessLevel... accessLevels) throws ForbiddenAccessException {
		for (AccessLevel level : accessLevels) {
			if (user.isAuthorized(level)) {
				return;
			}
		}

		throw new ForbiddenAccessException();
	}

}
