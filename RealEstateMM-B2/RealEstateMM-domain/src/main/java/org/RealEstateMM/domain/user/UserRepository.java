package org.RealEstateMM.domain.user;

import java.util.List;

import org.RealEstateMM.domain.user.exceptions.UserWithPseudonymAlreadyStoredException;

public abstract class UserRepository {

	public User getUserWithPseudonym(String pseudonym) {
		if (contains(pseudonym)) {
			return findUserWithPseudonym(pseudonym);
		} else {
			throw new UserNotFoundException(pseudonym);
		}
	}

	protected abstract boolean contains(String pseudonym);

	protected abstract User findUserWithPseudonym(String pseudonym);

	public abstract List<User> getAllUsers();

	public void addUser(User user) {
		if (contains(user.getPseudonym())) {
			throw new UserWithPseudonymAlreadyStoredException(user.getPseudonym());
		}

		add(user);
	}

	protected abstract void add(User user);

	public void replaceUser(User user) {
		removeUserWithPseudonym(user.getPseudonym());
		add(user);
	}

	protected abstract void removeUserWithPseudonym(String pseudonym);

}
