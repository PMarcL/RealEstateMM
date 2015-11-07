package org.RealEstateMM.domain.property.search;

public class InvalidFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String EXCEPTION_MESSAGE = "The provided search filter is invalid.";

	public InvalidFilterException() {
		super(EXCEPTION_MESSAGE);
	}

}
