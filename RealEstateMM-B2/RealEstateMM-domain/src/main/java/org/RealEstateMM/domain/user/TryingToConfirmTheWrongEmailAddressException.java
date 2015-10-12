package org.RealEstateMM.domain.user;

public class TryingToConfirmTheWrongEmailAddressException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TryingToConfirmTheWrongEmailAddressException(String emailAddressSent, String currentEmailAddress) {
		super("Trying to confirm the address: '" + emailAddressSent + "' but the currentAddress is: '"
				+ currentEmailAddress + "'.");
	}

}
