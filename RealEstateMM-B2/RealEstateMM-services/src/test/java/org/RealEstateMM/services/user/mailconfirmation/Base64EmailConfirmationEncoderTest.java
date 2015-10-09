package org.RealEstateMM.services.user.mailconfirmation;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class Base64EmailConfirmationEncoderTest {

	private static final String A_PSEUDO = "John123";
	private static final User A_USER = mock(User.class);

	private Base64EmailConfirmationEncoder emailConfirmationEncoder;

	@Before
	public void setUp() throws Exception {
		given(A_USER.getEmailAddress()).willReturn("someEmailAddress@machin.com");
		given(A_USER.getPseudonym()).willReturn(A_PSEUDO);

		emailConfirmationEncoder = new Base64EmailConfirmationEncoder();

	}

	@Test
	public void givenAUserWhenGetConfirmationCodeThenReturnAlwaysTheSameRetult() {
		String code1 = emailConfirmationEncoder.getConfirmationCode(A_USER);
		String code2 = emailConfirmationEncoder.getConfirmationCode(A_USER);
		assertEquals(code1, code2);
	}

	@Test // This is not a unit test but I didn't know how to test it without
			// copy/pasting the code from the getConfirmationCode method
	public void givenAValidConfirmationCodeWhenExtractPseudonymThenReturnThePseudonymEncodedInIt() {
		String confirmationCode = emailConfirmationEncoder.getConfirmationCode(A_USER);
		String extractedPseudo = emailConfirmationEncoder.extractPseudonymFromConfirmationCode(confirmationCode);
		assertEquals(A_PSEUDO, extractedPseudo);
	}

}
