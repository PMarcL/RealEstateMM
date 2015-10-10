package org.RealEstateMM.services.user.exceptions;

public class UserDoesNotExistException extends Exception {

	private static final String EXCEPTION_MESSAGE = "The specified user does not exist";

	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException() {
		super(EXCEPTION_MESSAGE);
	}

}
