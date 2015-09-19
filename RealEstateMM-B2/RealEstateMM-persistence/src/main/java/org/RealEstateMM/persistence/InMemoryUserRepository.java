package org.RealEstateMM.persistence;

import java.util.ArrayList;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.UserRepository;

public class InMemoryUserRepository implements UserRepository {

	private ArrayList<User> users;

	public InMemoryUserRepository() {
		users = new ArrayList<User>();
	}

	@Override
	public User getUserWithPseudoAndPassword(String userPseudo, String password) {
		return null;
	}

	@Override
	public User addUser(User user) {
		if (userIsInRepository(user)) {
			users.add(user);
		}

		return user;
	}

	public int getSize() {
		return users.size();
	}

	private boolean userIsInRepository(User user) {
		for (User aUser : users) {
			if (aUser.pseudonym.equals(user.pseudonym)) {
				return false;
			}
		}
		return true;
	}
}
