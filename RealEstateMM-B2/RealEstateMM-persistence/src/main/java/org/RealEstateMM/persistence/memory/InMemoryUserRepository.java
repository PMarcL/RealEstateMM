package org.RealEstateMM.persistence.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;

public class InMemoryUserRepository extends UserRepository {

	private Map<String, User> users;

	public InMemoryUserRepository() {
		users = new HashMap<String, User>();
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}

	public int size() {
		return users.size();
	}

	@Override
	public Optional<User> getUserWithPseudonym(String pseudonym) {
		if (!contains(pseudonym)) {
			return Optional.empty();
		}

		return Optional.of(users.get(pseudonym));
	}

	@Override
	protected boolean contains(String pseudonym) {
		return users.containsKey(pseudonym);
	}

	@Override
	protected void add(User user) {
		users.put(user.pseudonym, user);
	}

}
