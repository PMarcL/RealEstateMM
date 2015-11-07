package org.RealEstateMM.persistence.xml;

public class InvalidXmlFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The XML file contains invalid information.";

	public InvalidXmlFileException() {
		super(MESSAGE);
	}

}
