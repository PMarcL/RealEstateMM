package org.RealEstateMM.domain.message;

public class UserIsNotTheRecipient extends Exception {

	private static final long serialVersionUID = 1L;

	public UserIsNotTheRecipient() {
		super("The user is not the recipient of the message therefore it can not modify it");
	}
}
