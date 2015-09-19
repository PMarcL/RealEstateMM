package org.RealEstateMM.services;

public class ExistingPseudoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExistingPseudoException(String pseudo) {
		super("The pseudonym " + pseudo + " is already used.");
	}

}
