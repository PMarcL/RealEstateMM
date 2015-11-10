package org.RealEstateMM.persistence.memory;

import java.util.HashMap;
import java.util.Map;

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
	protected boolean contains(String pseudonym) {
		return users.containsKey(pseudonym);
	}

	@Override
	protected void add(User user) {
		users.put(user.getPseudonym(), user);
	}

	@Override
	protected void removeUserWithPseudonym(String pseudonym) {
		users.remove(pseudonym);
	}

	@Override
	protected User findUserWithPseudonym(String pseudonym) {
		return users.get(pseudonym);
	}

}
