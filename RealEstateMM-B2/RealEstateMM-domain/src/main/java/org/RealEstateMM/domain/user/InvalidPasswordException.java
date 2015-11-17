package org.RealEstateMM.domain.user;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "You specified the wrong password for this user";

	public InvalidPasswordException() {
		super(EXCEPTION_MESSAGE);
	}
}
