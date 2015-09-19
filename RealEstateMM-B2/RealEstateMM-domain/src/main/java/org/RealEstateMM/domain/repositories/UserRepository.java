package org.RealEstateMM.domain.repositories;

import org.RealEstateMM.domain.models.user.User;

public interface UserRepository {

	public User getUserWithPseudoAndPassword(String userPseudo, String password);

	User addUser(User user);

}
