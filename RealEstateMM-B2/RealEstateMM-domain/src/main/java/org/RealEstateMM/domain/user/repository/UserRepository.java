package org.RealEstateMM.domain.user.repository;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;

public abstract class UserRepository {

	public abstract Optional<User> getUserWithPseudonym(String pseudonym);

	public void persistUser(User user) {
		if (contains(user.getPseudonym())) {
			throw new UserWithPseudonymAlreadyStoredException(user.getPseudonym());
		}

		add(user);
	}

	protected abstract boolean contains(String pseudonym);

	protected abstract void add(User user);
}
