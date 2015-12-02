package org.RealEstateMM.domain.message;

public class UserIsNotASellerException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserIsNotASellerException(String userPseudonym) {
		super("The user " + userPseudonym + " is not a seller");
	}
}
