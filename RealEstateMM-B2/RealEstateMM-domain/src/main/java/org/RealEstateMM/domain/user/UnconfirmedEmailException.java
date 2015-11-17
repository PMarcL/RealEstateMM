package org.RealEstateMM.domain.user;

public class UnconfirmedEmailException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_MESSAGE = "Your email is still unconfirmed";

	public UnconfirmedEmailException() {
		super(EXCEPTION_MESSAGE);
	}

}
