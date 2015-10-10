package org.RealEstateMM.services.property;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PropertyAddressValidatorTest {
	
	private PropertyAddressValidator validator;
	private final String A_VALID_ZIP_CODE = "G6G6G6";
	private final String AN_INVALID_ZIP_CODE = "t(^.^t)";
	
	@Before
	public void init(){
		validator = new PropertyAddressValidator();
	}
	
	@Test
	public void givenAValidZipCodeWhenValidatingTheZipCodeThenTheValidatorShouldReturnTrue(){
		assertTrue(validator.zipCodeIsValid(A_VALID_ZIP_CODE));
	}
	
	@Test
	public void givenANInvalidZipCodeWhenValidatingTheZipCodeThenTheValidatorShouldReturnFalse(){
		assertFalse(validator.zipCodeIsValid(AN_INVALID_ZIP_CODE));
	}
}
