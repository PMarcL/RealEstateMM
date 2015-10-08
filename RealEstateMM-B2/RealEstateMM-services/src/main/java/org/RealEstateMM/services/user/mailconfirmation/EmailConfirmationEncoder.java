package org.RealEstateMM.services.user.mailconfirmation;

import org.RealEstateMM.domain.user.User;

public interface EmailConfirmationEncoder {

	String getConfirmationCode(User user);

}
