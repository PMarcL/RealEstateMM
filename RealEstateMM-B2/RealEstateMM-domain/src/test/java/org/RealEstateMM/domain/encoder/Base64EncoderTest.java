package org.RealEstateMM.domain.encoder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Base64EncoderTest {

	private static final String A_STRING = "some relatively long string";

	private Base64Encoder encoder;

	@Before
	public void setUp() {
		encoder = new Base64Encoder();
	}

	@Test
	public void givenAUserWhenGetConfirmationCodeThenReturnAlwaysTheSameRetult() {
		String code1 = encoder.encode(A_STRING);
		String code2 = encoder.encode(A_STRING);
		assertEquals(code1, code2);
	}

	@Test // This is not a unit test but I didn't know how to test it without
			// copy/pasting the code from the getConfirmationCode method
	public void givenAValidConfirmationCodeWhenExtractPseudonymThenReturnThePseudonymEncodedInIt() {
		String confirmationCode = encoder.encode(A_STRING);
		String extractedPseudo = encoder.decode(confirmationCode);
		assertEquals(A_STRING, extractedPseudo);
	}

}
