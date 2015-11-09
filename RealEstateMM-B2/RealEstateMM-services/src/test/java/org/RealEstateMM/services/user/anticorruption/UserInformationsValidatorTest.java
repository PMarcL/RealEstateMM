package org.RealEstateMM.services.user.anticorruption;

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
	private final String INVALID_NAME2 = "bob123";
	private final String INVALID_USER_TYPE = "abc";

	private final String EMPTY_STRING = "";

	private UserInformationsValidator validator;

	@Before
	public void initialisation() {
		validator = new UserInformationsValidator();
	}

	@Test
	public void givenAValidEmailWhenVerifyEmailThenReturnsTrue() {
		assertTrue(validator.isEmailValid(VALID_EMAIL));
	}

	@Test
	public void givenAnInvalidEmailWhenVerifyEmailThenReturnsFalse() {
		assertFalse(validator.isEmailValid(INVALID_EMAIL));
		assertFalse(validator.isEmailValid(null));
		assertFalse(validator.isEmailValid(EMPTY_STRING));
	}

	@Test
	public void givenAValidPhoneNumberWhenVerifyPhoneNumberThenReturnsTrue() {
		assertTrue(validator.isPhoneNumberValid(VALID_PHONENUMBER1));
		assertTrue(validator.isPhoneNumberValid(VALID_PHONENUMBER2));
		assertTrue(validator.isPhoneNumberValid(VALID_PHONENUMBER3));
	}

	@Test
	public void givenAnInvalidPhoneNumberWhenVerifyPhoneNumberThenReturnsFalse() {
		assertFalse(validator.isPhoneNumberValid(INVALID_PHONENUMBER));
	}

	@Test
	public void givenAValidNameWhenVerifyNameThenReturnsTrue() {
		assertTrue(validator.nameIsValid(VALID_NAME));
	}

	@Test
	public void givenAnInvalidNameWhenVerifyNameThenReturnsFalse() {
		assertFalse(validator.nameIsValid(INVALID_NAME1));
		assertFalse(validator.nameIsValid(INVALID_NAME2));
		assertFalse(validator.nameIsValid(EMPTY_STRING));
		assertFalse(validator.nameIsValid(null));
	}

	@Test
	public void givenAValidUserTypeWhenVerifyUserTypeThenReturnsTrue() {
		assertTrue(validator.isUserTypeValid(UserType.ADMIN));
		assertTrue(validator.isUserTypeValid(UserType.SELLER));
		assertTrue(validator.isUserTypeValid(UserType.BUYER));
	}

	@Test
	public void givenNullWhenVerifyUserTypeThenReturnsFalse() {
		assertFalse(validator.isUserTypeValid(INVALID_USER_TYPE));
		assertFalse(validator.isUserTypeValid(EMPTY_STRING));
		assertFalse(validator.isUserTypeValid(null));
	}

}
