package org.RealEstateMM.domain.user;

@SuppressWarnings("serial")
public class PhoneNumberFormatException extends RuntimeException {

	public PhoneNumberFormatException(String invalidPhoneNumber) {
		super(invalidPhoneNumber + " is not a valid phone number.");
	}

}
