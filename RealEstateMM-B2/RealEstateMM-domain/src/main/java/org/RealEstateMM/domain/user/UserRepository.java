package org.RealEstateMM.domain.user;

public interface UserRepository {

	public UserInformations getUserWithPseudoAndPassword(String userPseudo, String password);

}
