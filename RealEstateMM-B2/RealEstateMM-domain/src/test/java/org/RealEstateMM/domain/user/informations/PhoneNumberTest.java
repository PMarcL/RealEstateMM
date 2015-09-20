package org.RealEstateMM.domain.user.informations;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.informations.PhoneNumber;
import org.RealEstateMM.domain.user.informations.PhoneNumberFormatException;
import org.junit.Test;

public class PhoneNumberTest {

	@Test
	public void givenAValidPhoneNumberWhenCreatedShouldNotThrowException() {
		new PhoneNumber("4185551234");
	}

	@Test(expected = PhoneNumberFormatException.class)
	public void givenPhoneNumberWithLessThan10DigitsWhenCreatedShouldThrowException() {
		new PhoneNumber("418555123");
	}

	@Test(expected = PhoneNumberFormatException.class)
	public void givenPhoneNumberWithMoreThan10DigitsWhenCreatedShouldThrowException() {
		new PhoneNumber("41855512345");
	}

	@Test
	public void ignoresParenthesisHyphensAndSpaces() {
		PhoneNumber phone = new PhoneNumber("(418) 555-1234");
		assertEquals("4185551234", phone.toString());
	}

	@Test
	public void givenTwoPhoneNumberWithSameValueWhenComparingShouldBeEquals() {
		final String PHONE_NUMBER = "4185551234";
		PhoneNumber phoneNumber1 = new PhoneNumber(PHONE_NUMBER);
		PhoneNumber phoneNumber2 = new PhoneNumber(PHONE_NUMBER);
		assertEquals(phoneNumber1, phoneNumber2);
	}

	@Test
	public void givenTwoPhoneNumberWithDifferentValuesWhenComparingShouldNotBeEquals() {
		PhoneNumber phoneNumber1 = new PhoneNumber("4185551234");
		PhoneNumber phoneNumber2 = new PhoneNumber("5145559874");
		assertNotEquals(phoneNumber1, phoneNumber2);
	}

	@Test
	public void givenObjectOfAnotherTypeWhenComparingShouldReturnFalse() {
		String objectOfAnotherType = "";
		PhoneNumber phoneNumber = new PhoneNumber("4185551234");
		assertNotEquals(phoneNumber, objectOfAnotherType);
	}
}
