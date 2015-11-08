package org.RealEstateMM.domain.user;

import java.util.Optional;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserAuthorizations {

	private UserRepository userRepository;

	public UserAuthorizations(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean isUserAuthorized(String pseudonym, AccessLevel accessLevel) {
		Optional<User> user = userRepository.getUserWithPseudonym(pseudonym);
		if (user.isPresent()) {
			return user.get().isAuthorized(accessLevel);
		} else {
			return false;
		}
	}

}
