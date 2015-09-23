package org.RealEstateMM.domain.helpers;

import org.RealEstateMM.domain.user.UserBuilder;

public class DefaultUserBuilder extends UserBuilder {

	public static final String PSEUDO = "JohnD90";
	public static final String EMAIL = "example@hotmail.com";
	public static final String FIRST_NAME = "John";
	public static final String LAST_NAME = "Doe";
	public static final String PHONE_NUMBER = "(819) 418-5739";

	public DefaultUserBuilder() {
		withPseudonym(PSEUDO);
		withFirstName(FIRST_NAME);
		withLastName(LAST_NAME);
		withEmail(EMAIL);
		withPhoneNumber(PHONE_NUMBER);
	}

}
