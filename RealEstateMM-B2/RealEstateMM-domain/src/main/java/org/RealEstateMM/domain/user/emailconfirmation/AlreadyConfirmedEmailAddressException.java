package org.RealEstateMM.domain.user.emailconfirmation;

public class AlreadyConfirmedEmailAddressException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyConfirmedEmailAddressException() {
		super("The email associated to this confirmation code has already been confirmed");
	}

}
