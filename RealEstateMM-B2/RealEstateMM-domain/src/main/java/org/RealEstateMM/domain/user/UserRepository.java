package org.RealEstateMM.domain.user;

import java.util.Optional;

import org.RealEstateMM.domain.user.exceptions.UserWithPseudonymAlreadyStoredException;

public abstract class UserRepository {

	public abstract Optional<User> getUserWithPseudonym(String pseudonym);

	public void addUser(User user) {
		if (contains(user.getPseudonym())) {
			throw new UserWithPseudonymAlreadyStoredException(user.getPseudonym());
		}

		add(user);
	}

	protected abstract boolean contains(String pseudonym);

	protected abstract void add(User user);

	public void replaceUser(User user) {
		removeUserWithPseudonym(user.getPseudonym());
		add(user);
	}

	protected abstract void removeUserWithPseudonym(String pseudonym);
}
