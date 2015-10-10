package org.RealEstateMM.emailsender;

public class CouldNotSendMailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "Could not send mail to your address";

	public CouldNotSendMailException() {
		super(EXCEPTION_MESSAGE);
	}

}
