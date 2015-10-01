package org.RealEstateMM.authentication.session;

public class TokenInvalidException extends Exception {

	private static final long serialVersionUID = 1L;

	public TokenInvalidException() {
		super("Invalid Token. No session associated.");
	}

}
