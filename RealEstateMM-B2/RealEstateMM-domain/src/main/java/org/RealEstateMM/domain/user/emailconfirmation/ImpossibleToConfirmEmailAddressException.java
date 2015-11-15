package org.RealEstateMM.domain.user.emailconfirmation;

public class ImpossibleToConfirmEmailAddressException extends Exception {

	private static final long serialVersionUID = 1L;

	public ImpossibleToConfirmEmailAddressException(Exception exception) {
		super("Impossible to confirm the email address with the provided confirmation code:\n\t-"
				+ exception.getMessage());
	}

}
