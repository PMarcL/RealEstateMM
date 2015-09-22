package org.RealEstateMM.domain.property.informations;

@SuppressWarnings("serial")
public class InvalidZipCodeFormatException extends RuntimeException {

	public InvalidZipCodeFormatException(String invalidZipCode) {
		super(invalidZipCode + " is not a valid canadian Zip Code.");
	}
}
