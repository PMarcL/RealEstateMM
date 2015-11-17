package org.RealEstateMM.domain.user;

public class ImpossibleToConfirmEmailAddressException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImpossibleToConfirmEmailAddressException(Exception exception) {
		super("Impossible to confirm the email address with the provided confirmation code:\n\t-"
				+ exception.getMessage());
	}

}
