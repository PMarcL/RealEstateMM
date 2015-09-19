package org.RealEstateMM.persistence;

import java.util.ArrayList;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.domain.user.UserRepository;

public class InMemoryUserRepository implements UserRepository {

	private ArrayList<UserAccount> users;

	public InMemoryUserRepository() {
		users = new ArrayList<UserAccount>();
	}

	@Override
	public UserAccount getUserWithPseudoAndPassword(String userPseudo, String password) {
		return null;
	}

	@Override
	public void addUser(UserAccount user) {
		if (userIsInRepository(user)) {
			users.add(user);
		}
	}

	public int getSize() {
		return users.size();
	}

	private boolean userIsInRepository(UserAccount user) {
		for (UserAccount aUser : users) {
			if (aUser.pseudonym.equals(user.pseudonym)) {
				return false;
			}
		}
		return true;
	}
}
