package org.RealEstateMM.domain.user;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserAuthorizations {

	private UserRepository userRepository;

	public UserAuthorizations(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean isUserAuthorized(String pseudonym, AccessLevel accessLevel) {
		User user = userRepository.getUserWithPseudonym(pseudonym);
		return user.isAuthorized(accessLevel);
	}

}
