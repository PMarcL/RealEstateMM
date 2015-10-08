package org.RealEstateMM.services.mail;

public class CouldNotSendMailException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "Could not send mail to your address";

	public CouldNotSendMailException() {
		super(EXCEPTION_MESSAGE);
	}

}
