package org.RealEstateMM.domain.user.informations;

@SuppressWarnings("serial")
public class InvalidEmailFormatException extends RuntimeException {

	public InvalidEmailFormatException(String invalidEmail) {
		super(invalidEmail + " is not a valid email address.");
	}
}
