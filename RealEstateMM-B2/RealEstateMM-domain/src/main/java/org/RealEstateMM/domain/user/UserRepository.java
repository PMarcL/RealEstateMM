package org.RealEstateMM.domain.user;

public interface UserRepository {

	public UserAccount getUserWithPseudoAndPassword(String userPseudo, String password);

}
