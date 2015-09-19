package org.RealEstateMM.services.anticorruption;

@SuppressWarnings("serial")
public class InvalidUserInformationsException extends RuntimeException {

	public InvalidUserInformationsException(String invalidField) {
		super("The field " + invalidField + " is either empty or invalid.");
	}
}
