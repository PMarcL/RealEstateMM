package org.RealEstateMM.domain.user;

import java.util.Optional;

public interface UserRepository {

	public Optional<UserAccount> getUserWithPseudonym(String pseudonym);

	public void addUser(UserAccount user);
}
