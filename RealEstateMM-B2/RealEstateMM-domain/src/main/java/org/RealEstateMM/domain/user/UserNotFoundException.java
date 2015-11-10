package org.RealEstateMM.domain.user;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String pseudonym) {
		super(String.format("There's not user with pseudonym %s.", pseudonym));
	}

}
