package org.RealEstateMM.domain.user;

public interface UserRepository {

	public User getUserWithPseudoAndPassword(String userPseudo, String password);

	public User create(User user);

}
