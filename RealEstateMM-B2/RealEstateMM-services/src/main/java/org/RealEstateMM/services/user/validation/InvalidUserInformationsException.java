package org.RealEstateMM.services.user.validation;

@SuppressWarnings("serial")
public class InvalidUserInformationsException extends Exception {

	public InvalidUserInformationsException(String invalidField) {
		super("The field " + invalidField + " is either empty or invalid.");
	}
}
