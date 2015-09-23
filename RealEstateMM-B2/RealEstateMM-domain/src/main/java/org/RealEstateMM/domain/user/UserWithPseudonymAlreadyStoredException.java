package org.RealEstateMM.domain.user;

public class UserWithPseudonymAlreadyStoredException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserWithPseudonymAlreadyStoredException(String pseudonym) {
		super(String.format("A user with pseudonym %s has already been stored.", pseudonym));
	}

}
