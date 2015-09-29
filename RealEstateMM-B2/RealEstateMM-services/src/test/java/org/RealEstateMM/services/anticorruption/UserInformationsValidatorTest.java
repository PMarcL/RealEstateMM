package org.RealEstateMM.services.anticorruption;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserType;
import org.junit.Before;
import org.junit.Test;

public class UserInformationsValidatorTest {

	private final String VALID_EMAIL = "email@gmail.com";
	private final String INVALID_EMAIL = "hello";
	private final String VALID_PHONENUMBER1 = "(819) 244-4353";
	private final String VALID_PHONENUMBER2 = "819 244-4353";
	private final String VALID_PHONENUMBER3 = "4182346666";
	private final String INVALID_PHONENUMBER = "(819) 44-435";
	private final String VALID_NAME = "John";
	private final String INVALID_NAME1 = "";
	private final String INVALID_NAME2 = null;
	private final String INVALID_USER_TYPE = "";

	private UserInformationsValidator validator;

	@Before
	public void initialisation() {
		validator = new UserInformationsValidator();
	}

	@Test
	public void givenAValidEmailWhenVerifyEmailThenReturnsTrue() {
		assertTrue(validator.emailIsValid(VALID_EMAIL));
	}

	@Test
	public void givenAnInvalidEmailWhenVerifyEmailThenReturnsFalse() {
		assertFalse(validator.emailIsValid(INVALID_EMAIL));
	}

	@Test
	public void givenAValidPhoneNumberWhenVerifyPhoneNumberThenReturnsTrue() {
		assertTrue(validator.phoneNumberIsValid(VALID_PHONENUMBER1));
		assertTrue(validator.phoneNumberIsValid(VALID_PHONENUMBER2));
		assertTrue(validator.phoneNumberIsValid(VALID_PHONENUMBER3));
	}

	@Test
	public void givenAnInvalidPhoneNumberWhenVerifyPhoneNumberThenReturnsFalse() {
		assertFalse(validator.phoneNumberIsValid(INVALID_PHONENUMBER));
	}

	@Test
	public void givenAValidNameWhenVerifyNameThenReturnsTrue() {
		assertTrue(validator.stringIsValid(VALID_NAME));
	}

	@Test
	public void givenAnInvalidNameWhenVerifyNameThenReturnsFalse() {
		assertFalse(validator.stringIsValid(INVALID_NAME1));
		assertFalse(validator.stringIsValid(INVALID_NAME2));
	}

	@Test
	public void givenAValidUserTypeWhenVerifyUserTypeThenReturnsTrue() {
		assertTrue(validator.userTypeIsValid(UserType.ADMIN));
		assertTrue(validator.userTypeIsValid(UserType.SELLER));
		assertTrue(validator.userTypeIsValid(UserType.BUYER));
	}

	@Test
	public void givenAnInvalidUserTypeWhenVerifyUserTypeThenReturnsFalse() {
		assertFalse(validator.userTypeIsValid(INVALID_USER_TYPE));
	}
}
