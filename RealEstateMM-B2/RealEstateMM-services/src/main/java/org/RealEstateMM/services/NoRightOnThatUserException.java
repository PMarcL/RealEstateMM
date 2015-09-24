package org.RealEstateMM.services;

public class NoRightOnThatUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRightOnThatUserException() {
		super("You dont have the required right over the requested User");
	}

}
