package org.RealEstateMM.domain.user;

import java.util.Optional;

public abstract class UserRepository {

	public abstract Optional<User> getUserWithPseudonym(String pseudonym);

	public void addUser(User user) {
		if (contains(user.pseudonym)) {
			throw new UserWithPseudonymAlreadyStoredException(user.pseudonym);
		}

		add(user);
	}

	protected abstract boolean contains(String pseudonym);

	protected abstract void add(User user);
}
