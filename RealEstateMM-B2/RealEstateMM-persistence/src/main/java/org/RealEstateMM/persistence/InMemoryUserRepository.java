package org.RealEstateMM.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.user.User;

public class InMemoryUserRepository implements org.RealEstateMM.domain.user.UserRepository {

	private Map<String, User> users;

	public InMemoryUserRepository() {
		users = new HashMap<String, User>();
	}

	@Override
	public Optional<User> getUserWithPseudonym(String pseudonym) {
		if (!userExist(pseudonym)) {
			return Optional.empty();
		}

		return Optional.of(users.get(pseudonym));
	}

	private boolean userExist(String pseudonym) {
		return users.containsKey(pseudonym);
	}

	@Override
	public void addUser(User user) {
		if (userExist(user.pseudonym)) {
			throw new PseudonymAlreadyUsedException();
		}

		users.put(user.pseudonym, user);
	}

	public int getSize() {
		return users.size();
	}
}
