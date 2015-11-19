package org.RealEstateMM.domain.user;

public class ExistingUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingUserException(String pseudonym) {
		super(String.format("A user with pseudonym %s has already been stored.", pseudonym));
	}

}
