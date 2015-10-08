package org.RealEstateMM.services.anticorruption;

@SuppressWarnings("serial")
public class InvalidPropertyInformationException extends RuntimeException {

	public InvalidPropertyInformationException(String invalidInfo) {
		super("The field " + invalidInfo + " is empty or invalid.");
	}
}