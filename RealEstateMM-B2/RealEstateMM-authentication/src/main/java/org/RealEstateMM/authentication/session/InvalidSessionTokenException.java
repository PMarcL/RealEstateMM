package org.RealEstateMM.authentication.session;

public class InvalidSessionTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidSessionTokenException() {
		super("Invalid Token. No session associated.");
	}

}
