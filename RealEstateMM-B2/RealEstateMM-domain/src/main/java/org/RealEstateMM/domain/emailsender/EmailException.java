package org.RealEstateMM.domain.emailsender;

public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "Error while sending email.";

	public EmailException(Throwable cause) {
		super(EXCEPTION_MESSAGE, cause);
	}

}
