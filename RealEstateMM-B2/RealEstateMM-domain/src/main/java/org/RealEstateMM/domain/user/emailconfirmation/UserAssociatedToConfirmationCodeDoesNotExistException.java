package org.RealEstateMM.domain.user.emailconfirmation;

public class UserAssociatedToConfirmationCodeDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAssociatedToConfirmationCodeDoesNotExistException(String pseudo) {
		super("The user associated: '" + pseudo + "' to the provided email confirmation code does not exist");
	}

}
