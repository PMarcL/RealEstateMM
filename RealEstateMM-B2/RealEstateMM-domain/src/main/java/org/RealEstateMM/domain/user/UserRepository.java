package org.RealEstateMM.domain.user;

import java.util.Optional;

public interface UserRepository {

	public Optional<User> getUserWithPseudonym(String pseudonym);

	public User addUser(User user);
}
