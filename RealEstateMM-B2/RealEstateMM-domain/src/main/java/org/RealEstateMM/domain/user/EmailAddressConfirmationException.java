package org.RealEstateMM.domain.user;

public class EmailAddressConfirmationException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailAddressConfirmationException(Throwable cause) {
		super("Impossible to confirm the user's email address.", cause);
	}

}
