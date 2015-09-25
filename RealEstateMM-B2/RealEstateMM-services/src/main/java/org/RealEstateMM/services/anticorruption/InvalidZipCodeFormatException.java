package org.RealEstateMM.services.anticorruption;

@SuppressWarnings("serial")
public class InvalidZipCodeFormatException extends RuntimeException {

	public InvalidZipCodeFormatException(String invalidZipCode) {
		super(invalidZipCode + " is not a valid canadian Zip Code.");
	}
}
