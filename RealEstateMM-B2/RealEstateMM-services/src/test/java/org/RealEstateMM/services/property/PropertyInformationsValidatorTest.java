package org.RealEstateMM.services.property;

import static org.junit.Assert.*;

import java.util.Calendar;

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
	
	private int A_NUMBER_OF_BATHROOMS = 2;
	private int A_NUMBER_OF_BEDROOMS = 2;
	private int A_VALID_NUMBER_OF_TOTAL_ROOMS = A_NUMBER_OF_BATHROOMS + A_NUMBER_OF_BEDROOMS + 1;
	private int AN_INVALID_NUMBER_OF_TOTAL_ROOMS = A_NUMBER_OF_BATHROOMS + A_NUMBER_OF_BEDROOMS - 1;
	
	private int YEAR_OF_CONSTRUCTION_OF_OLDEST_BUILDING_IN_CANADA = 1637; //According to wikipedia.org
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	private int A_VALID_YEAR_OF_CONSTRUCTION = 1995;
	private int A_YEAR_OF_CONSTRUCTION_TOO_OLD = YEAR_OF_CONSTRUCTION_OF_OLDEST_BUILDING_IN_CANADA -1;
	private int A_YEAR_OF_CONSTRUCTION_IN_THE_FUTUR = currentYear + 1;

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
	
	@Test
	public void givenAValidTotalNumberOfRoomWhenCheckingNumberOfRoomValidityThenShouldReturnTrue(){
		assertTrue(validator.totalNumberOfRoomsIsValid(A_NUMBER_OF_BATHROOMS, A_NUMBER_OF_BEDROOMS, A_VALID_NUMBER_OF_TOTAL_ROOMS));
	}
	
	@Test
	public void givenAnInValidTotalNumberOfRoomWhenCheckingNumberOfRoomValidityThenShouldReturnFalse(){
		assertFalse(validator.totalNumberOfRoomsIsValid(A_NUMBER_OF_BATHROOMS, A_NUMBER_OF_BEDROOMS, AN_INVALID_NUMBER_OF_TOTAL_ROOMS));
	}
	
	@Test
	public void givenAConstructionYearBetweenTheYearOfConstructionOfTheOldestBuildingInCanadaAndTodaysDateWhenCheckingTheConstructionYearValidityThenShouldReturnTrue(){
		assertTrue(validator.yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION));
	}
	
	@Test
	public void givenAConstructionYearOlderThenTheYearOfConstructionOfTheOldestBuildingInCanadaWhenCheckingTheConstructionYearValidityThenShouldReturnFalse(){
		assertFalse(validator.yearOfConstructionIsValid(A_YEAR_OF_CONSTRUCTION_TOO_OLD));
	}
	
	@Test
	public void givenAConstructionYearInTheFuturWhenCheckingTheConstructionYearValidityThenShouldReturnFalse(){
		assertFalse(validator.yearOfConstructionIsValid(A_YEAR_OF_CONSTRUCTION_IN_THE_FUTUR));
	}
}
