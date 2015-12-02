package org.RealEstateMM.domain.user;

public class InvalidConfirmationCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidConfirmationCodeException() {
		super("This provided email confirmation code is not valid.");
	}

}
