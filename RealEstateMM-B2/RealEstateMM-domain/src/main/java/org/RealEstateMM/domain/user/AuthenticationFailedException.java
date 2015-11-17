package org.RealEstateMM.domain.user;

public class AuthenticationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthenticationFailedException(Throwable cause) {
		super("Unable to authenticate user.", cause);
	}
}
