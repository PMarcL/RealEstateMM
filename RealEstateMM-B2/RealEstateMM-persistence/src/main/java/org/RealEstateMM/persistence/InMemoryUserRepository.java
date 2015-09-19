package org.RealEstateMM.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.domain.user.UserRepository;

public class InMemoryUserRepository implements UserRepository {

	private Map<String, UserAccount> users;

	public InMemoryUserRepository() {
		users = new HashMap<String, UserAccount>();
	}

	@Override
	public Optional<UserAccount> getUserWithPseudonym(String pseudonym) {
		if (!userExist(pseudonym)) {
			return Optional.empty();
		}

		return Optional.of(users.get(pseudonym));
	}

	private boolean userExist(String pseudonym) {
		return users.containsKey(pseudonym);
	}

	@Override
	public void addUser(UserAccount user) {
		if (userExist(user.pseudonym)) {
			throw new PseudonymAlreadyUsedException();
		}

		users.put(user.pseudonym, user);
	}

	public int getSize() {
		return users.size();
	}
}
