package org.RealEstateMM.domain.user.emailconfirmation;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCode;
import org.RealEstateMM.domain.user.emailconfirmation.EmailAddressConfirmationMessage;
import org.junit.Test;

public class EmailAddressConfirmationMessageTest {
	private final String BASE_URL = "http://localhost/";
	private final String RESOURCE_PATH = "user/emailConfirmation/";
	private final String CONFIRMATION_CODE_VALUE = "confirmationCode";

	@Test
	public void emailContainsEmailConfirmationUrl() {
		final String RECIPIENT_ADDRESS = "bobby143@gmail.com";
		ConfirmationCode confirmationCode = mock(ConfirmationCode.class);
		given(confirmationCode.toString()).willReturn(CONFIRMATION_CODE_VALUE);

		EmailAddressConfirmationMessage email = new EmailAddressConfirmationMessage(RECIPIENT_ADDRESS, confirmationCode,
				BASE_URL);

		assertTrue(stringContains(email.body, emailConfirmationUrl()));
	}

	private boolean stringContains(String source, String substring) {
		final int SUBSTRING_NOT_FOUND_INDEX = -1;
		return source.indexOf(substring) != SUBSTRING_NOT_FOUND_INDEX;
	}

	private String emailConfirmationUrl() {
		return BASE_URL + RESOURCE_PATH + CONFIRMATION_CODE_VALUE;
	}
}
