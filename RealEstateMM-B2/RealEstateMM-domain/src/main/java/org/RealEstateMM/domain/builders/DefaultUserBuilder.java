package org.RealEstateMM.domain.builders;

import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;

public class DefaultUserBuilder extends UserBuilder {

	private final String PSEUDO = "JohnD90";
	private final Email EMAIL = new Email("example@hotmail.com");
	private final Name NAME = new Name("John", "Doe");
	private final PhoneNumber PHONE_NUMBER = new PhoneNumber("(819) 418-5739");

	public DefaultUserBuilder() {
		super();
		withPseudonym(PSEUDO);
		withName(NAME);
		withEmail(EMAIL);
		withPhoneNumber(PHONE_NUMBER);
	}
}
