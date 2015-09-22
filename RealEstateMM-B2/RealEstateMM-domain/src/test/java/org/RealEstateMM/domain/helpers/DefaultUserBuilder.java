package org.RealEstateMM.domain.helpers;

import org.RealEstateMM.domain.user.UserBuilder;

public class DefaultUserBuilder extends UserBuilder {

	private final String PSEUDO = "JohnD90";
	private final String EMAIL = "example@hotmail.com";
	private final String FIRST_NAME = "John";
	private final String LAST_NAME = "Doe";
	private final String PHONE_NUMBER = "(819) 418-5739";

	public DefaultUserBuilder() {
		withPseudonym(PSEUDO);
		withFirstName(FIRST_NAME);
		withLastName(LAST_NAME);
		withEmail(EMAIL);
		withPhoneNumber(PHONE_NUMBER);
	}

}
