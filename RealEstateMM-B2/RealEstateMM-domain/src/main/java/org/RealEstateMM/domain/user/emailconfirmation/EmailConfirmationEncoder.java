package org.RealEstateMM.domain.user.emailconfirmation;

import org.RealEstateMM.domain.user.User;

public interface EmailConfirmationEncoder {

	String getConfirmationCode(User user);

	String extractPseudonymFromConfirmationCode(String confirmationCode);

}
