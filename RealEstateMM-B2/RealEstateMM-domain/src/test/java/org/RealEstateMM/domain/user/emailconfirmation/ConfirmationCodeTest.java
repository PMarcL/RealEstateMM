package org.RealEstateMM.domain.user.emailconfirmation;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.encoder.Encoder;
import org.junit.Before;
import org.junit.Test;

public class ConfirmationCodeTest {
	private final String PSEUDONYM = "bob134";
	private final String EMAIL_ADDRESS = "bob@gmail.com";
	private final String DECODED_CONFIRMATION_CODE_VALUE = String.format("%s;%s", PSEUDONYM, EMAIL_ADDRESS);
	private final String ENCODED_CONFIRMATION_CODE_VALUE = "confirmationCodeValue";

	private Encoder encoder;

	@Before
	public void setup() {
		encoder = mock(Encoder.class);
		given(encoder.decode(ENCODED_CONFIRMATION_CODE_VALUE)).willReturn(DECODED_CONFIRMATION_CODE_VALUE);
	}

	@Test
	public void whenBuiltFromPseudonymAndEmailShouldBeAbleToGetPseudonym() {
		ConfirmationCode code = createFromPseudoAndEmail();
		assertEquals(PSEUDONYM, code.getPseudonym());
	}

	@Test
	public void whenBuiltFromPseudonymAndEmailShouldBeAbleToGetEmailAddress() {
		ConfirmationCode code = createFromPseudoAndEmail();
		assertEquals(EMAIL_ADDRESS, code.getEmailAddress());
	}

	@Test
	public void whenBuiltFromPseudonymAndEmailShouldEncodeConfirmationCode() {
		given(encoder.encode(DECODED_CONFIRMATION_CODE_VALUE)).willReturn(ENCODED_CONFIRMATION_CODE_VALUE);
		ConfirmationCode code = createFromPseudoAndEmail();
		assertEquals(ENCODED_CONFIRMATION_CODE_VALUE, code.toString());
	}

	@Test
	public void whenBuiltFromConfirmationCodeValueShouldBeAbleToGetConfirmationCodeValue() {
		ConfirmationCode code = createFromConfirmationCodeValue();
		assertEquals(ENCODED_CONFIRMATION_CODE_VALUE, code.toString());
	}

	@Test
	public void whenBuiltFromConfirmationCodeValueShouldDecodeEmailAddressFromConfirmationCodeValue() {
		ConfirmationCode code = createFromConfirmationCodeValue();
		assertEquals(EMAIL_ADDRESS, code.getEmailAddress());
	}

	@Test
	public void whenBuiltFromConfirmationCodeValueShouldDecodePseudonymFromConfirmationCodeValue() {
		ConfirmationCode code = createFromConfirmationCodeValue();
		assertEquals(PSEUDONYM, code.getPseudonym());
	}

	@Test(expected = InvalidEmailConfirmationCodeException.class)
	public void givenInvalidConfirmationCodeValueWhenBuiltFromConfirmationCodeValueShouldThrowException() {
		final String INVALID_CONFIRMATION_CODE_VALUE = "invalidConfirmationCode";
		given(encoder.decode(ENCODED_CONFIRMATION_CODE_VALUE)).willReturn(INVALID_CONFIRMATION_CODE_VALUE);
		new ConfirmationCode(encoder, ENCODED_CONFIRMATION_CODE_VALUE);
	}

	private ConfirmationCode createFromConfirmationCodeValue() {
		return new ConfirmationCode(encoder, ENCODED_CONFIRMATION_CODE_VALUE);
	}

	private ConfirmationCode createFromPseudoAndEmail() {
		return new ConfirmationCode(encoder, PSEUDONYM, EMAIL_ADDRESS);
	}
}
