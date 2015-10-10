package org.RealEstateMM.domain.user.emailconfirmation;

public class InvalidEmailConfirmationCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidEmailConfirmationCodeException() {
		super("This provided email confirmation code is not valid.");
	}

}
