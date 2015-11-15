package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserAuthorizations {

	private UserRepository userRepository;

	public UserAuthorizations(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean isUserAuthorized(String pseudonym, AccessLevel... accessLevels) {
		User user = userRepository.getUserWithPseudonym(pseudonym);
		for (AccessLevel level : accessLevels) {
			if (user.isAuthorized(level)) {
				return true;
			}
		}

		return false;
	}

}
