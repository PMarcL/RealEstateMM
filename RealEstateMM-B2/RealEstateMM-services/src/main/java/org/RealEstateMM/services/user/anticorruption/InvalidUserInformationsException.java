package org.RealEstateMM.services.user.anticorruption;

@SuppressWarnings("serial")
public class InvalidUserInformationsException extends RuntimeException {

	public InvalidUserInformationsException(String invalidField) {
		super("The field " + invalidField + " is either empty or invalid.");
	}
}
