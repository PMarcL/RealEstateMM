package org.RealEstateMM.services.search;

public class InvalidSearchParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MESSAGE = "The provided search parameter is invalid.";

	public InvalidSearchParameterException() {
		super(EXCEPTION_MESSAGE);
	}

}
