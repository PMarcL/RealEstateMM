package org.RealEstateMM.domain.helpers;

import org.RealEstateMM.domain.user.UserBuilder;

public class DefaultUserBuilder extends UserBuilder {

	public DefaultUserBuilder() {
		withPseudonym(DefaultUserValue.PSEUDO);
		withFirstName(DefaultUserValue.FIRST_NAME);
		withLastName(DefaultUserValue.LAST_NAME);
		withEmail(DefaultUserValue.EMAIL);
		withPhoneNumber(DefaultUserValue.PHONE_NUMBER);
	}

}
