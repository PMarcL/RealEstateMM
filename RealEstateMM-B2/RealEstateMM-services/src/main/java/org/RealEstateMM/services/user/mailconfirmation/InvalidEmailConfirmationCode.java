package org.RealEstateMM.services.user.mailconfirmation;

public class InvalidEmailConfirmationCode extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidEmailConfirmationCode() {
		super("This email confirmation code is not valid.");
	}

}
