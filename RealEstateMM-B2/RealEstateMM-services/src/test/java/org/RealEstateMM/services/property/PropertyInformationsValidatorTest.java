package org.RealEstateMM.services.property;

import static org.junit.Assert.*;

import org.RealEstateMM.services.property.PropertyInformationsValidator;
import org.junit.Before;
import org.junit.Test;

public class PropertyInformationsValidatorTest {

	private PropertyInformationsValidator validator;
	private final String A_VALID_ZIP_CODE = "G6G6G6";
	private final String AN_INVALID_INFO = "t(^.^t)";
	private final String HOUSE = "house";
	private final String COMMERCIAL = "commercial";
	private final String MULTIPLEX = "multiplex";
	private final String FARM = "farm";
	private final String LAND = "land";
	private final String ONSALE = "on sale";
	private final String SOLD = "sold";
	private final int VALID_NUMBER_OF_ROOMS = 5;
	private int INVALID_NUMBER_OF_ROOMS = -1;

	@Before
	public void init() {
		validator = new PropertyInformationsValidator();
	}

	@Test
	public void givenAValidZipCodeWhenValidatingTheZipCodeThenTheValidatorShouldReturnTrue() {
		assertTrue(validator.zipCodeIsValid(A_VALID_ZIP_CODE));
	}

	@Test
	public void givenAnInvalidZipCodeWhenValidatingTheZipCodeThenTheValidatorShouldReturnFalse() {
		assertFalse(validator.zipCodeIsValid(AN_INVALID_INFO));
	}

	@Test
	public void givenAValidPropertyTypeDescriptionWhenValidatingPropertyTypeThenReturnsTrue() {
		assertTrue(validator.propertyTypeIsValid(HOUSE));
		assertTrue(validator.propertyTypeIsValid(COMMERCIAL));
		assertTrue(validator.propertyTypeIsValid(MULTIPLEX));
		assertTrue(validator.propertyTypeIsValid(FARM));
		assertTrue(validator.propertyTypeIsValid(LAND));
	}

	@Test
	public void givenAnInvalidPropertyTypeDescriptionWhenValidatingPropertyTypeThenReturnsFalse() {
		assertFalse(validator.propertyTypeIsValid(AN_INVALID_INFO));
	}

	@Test
	public void givenAValidPropertyStatusWhenValidatingPropertyStatusThenReturnsTrue() {
		assertTrue(validator.propertyStatusIsValid(ONSALE));
		assertTrue(validator.propertyStatusIsValid(SOLD));
	}

	@Test
	public void givenAnInvalidPropertyStatusWhenValidatingStatusThenReturnsFalse() {
		assertFalse(validator.propertyStatusIsValid(AN_INVALID_INFO));
	}

	@Test
	public void givenAValidNumberOfRoomsWhenCheckNumberOfRoomsValidityThenReturnsTrue() {
		assertTrue(validator.numberOfRoomsIsValid(VALID_NUMBER_OF_ROOMS));
	}

	@Test
	public void givenAnInvalidNumberOfRoomsWhenCheckNumberOfRoomsValidityThenReturnsFalse() {
		assertFalse(validator.numberOfRoomsIsValid(INVALID_NUMBER_OF_ROOMS));
	}
}
