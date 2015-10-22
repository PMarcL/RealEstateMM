package org.RealEstateMM.services.user.exceptions;

public class UnconfirmedEmailException extends Exception{
	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "Your email is still unconfirmed";

	public UnconfirmedEmailException() {
		super(EXCEPTION_MESSAGE);
	}

}
