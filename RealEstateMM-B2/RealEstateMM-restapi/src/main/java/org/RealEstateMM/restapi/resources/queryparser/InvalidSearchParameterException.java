package org.RealEstateMM.restapi.resources.queryparser;

public class InvalidSearchParameterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MESSAGE = "The provided search parameters are invalid.";

	public InvalidSearchParameterException() {
		super(EXCEPTION_MESSAGE);
	}

}
