package org.RealEstateMM.domain.user.informations;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.InvalidEmailFormatException;
import org.junit.Test;

public class EmailTest {

	@Test
	public void givenAValidEmailAddressWhenCreatedShouldNotThrowException() {
		new Email("john@gmail.com");
	}

	@Test(expected = InvalidEmailFormatException.class)
	public void givenAnEmailWithoutDomainWhenCreatedShouldThrowInvalidEmailFormatException() {
		new Email("john@");
	}

	@Test(expected = InvalidEmailFormatException.class)
	public void givenAnEmailWithoutAtCharWhenCreatedShouldThrowInvalidEmailFormatExceptoin() {
		new Email("johngmail.com");
	}

	@Test
	public void givenAValidEmailWithNumbersWhenCreatedShouldNotThrowException() {
		new Email("john22@hotmail.com");
	}

	@Test
	public void givenTwoIdenticalEmailWhenComparingShouldReturnTrue() {
		Email email1 = new Email("test@hotmail.com");
		Email email2 = new Email("test@hotmail.com");
		assertEquals(email1, email2);
	}

	@Test
	public void givenAnEmailAndANullObjectWhenComparingShouldReturnFalse() {
		Email email = new Email("test@hotmail.com");
		assertNotEquals(email, null);
	}

	@Test
	public void givenTwoDifferentEmailWhenComparingShouldReturnFalse() {
		Email email1 = new Email("test1@hotmail.com");
		Email email2 = new Email("test3@hotmail.com");
		assertNotEquals(email1, email2);
	}

	@Test
	public void givenAnValidEmailWhenGetStringShouldReturnStringContainingTheSameEmail() {
		Email email = new Email("test@hotmail.com");
		assertEquals("test@hotmail.com", email.toString());
	}
}
