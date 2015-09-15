package org.RealEstateMM.domain.user;

public interface UserRepository {

	public UserInformation getUserWithPseudoAndEmail(String userPseudo, String userEmail);

}
