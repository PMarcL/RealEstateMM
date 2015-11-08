package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class PropertyTest {

	private static final int EMPTY_NUMBER_OF_BATHROOMS = 0;
	private static final int EMPTY_TOTAL_NUMBER_OF_ROOMS = 0;
	private static final int EMPTY_NUMBER_OF_BEDROOMS = 0;
	private static final int EMPTY_NUMBER_OF_LEVEL = 0;
	private static final double EMPTY_LOT_DIMENSION = 0.00;
	private static final int EMPTY_YEAR_OF_CONSTRUCTION = 0;
	private static final double EMPTY_LIVING_SPACE_AREA = 0.00;
	private static final String EMPTY_BACKYARD_DIRECTION = "";
	private static final String EMPTY_DESCRIPTION = "";

	private static final int A_NUMBER_OF_BATHROOMS = 1;
	private static final int A_TOTAL_NUMBER_OF_ROOMS = 3;
	private static final int A_NUMBER_OF_BEDROOMS = 1;
	private static final int A_NUMBER_OF_LEVEL = 2;
	private static final double A_LOT_DIMENSION = 120.00;
	private static final int A_YEAR_OF_CONSTRUCTION = 2000;
	private static final double A_LIVING_SPACE_AREA = 50.00;
	private static final String A_BACKYARD_DIRECTION = "WEST";
	private static final String A_DESCRIPTION = "Good property";

	private static final double DELTA = 0.001;

	private Property property;

	private static final double A_PROPERTY_PRICE = 200000.00;
	private static final PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ON_SALE;
	private static final String OWNER_USERNAME = "Bob";
	private static final String NOT_THE_OWNER_USERNAME = "NotBob";
	private static final PropertyType A_PROPERTY_TYPE = PropertyType.HOUSE;

	private PropertyAddress propertyAddress;

	@Before
	public void setup() {
		propertyAddress = mock(PropertyAddress.class);

		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PROPERTY_PRICE, OWNER_USERNAME, A_PROPERTY_STATUS,
				new Date(), null);
	}

	@Test
	public void givenAPropertyWhenVerifyingItsOwnerWithAnInvalidOwnerThenItShouldReturnFalse() {
		assertFalse(property.isOwnedBy(NOT_THE_OWNER_USERNAME));
	}

	@Test
	public void givenAPropertyWhenVerifyingItsOwnerWithTheRightOwnerThenItShouldReturnTrue() {
		assertTrue(property.isOwnedBy(OWNER_USERNAME));
	}

	@Test
	public void givenANewPropertyThenItShouldHaveEmptyPropertyFeatures() {
		PropertyFeatures returnedFeatures = property.getFeatures();

		assertEquals(EMPTY_NUMBER_OF_BATHROOMS, returnedFeatures.numberOfBathrooms);
		assertEquals(EMPTY_NUMBER_OF_BEDROOMS, returnedFeatures.numberOfBedrooms);
		assertEquals(EMPTY_TOTAL_NUMBER_OF_ROOMS, returnedFeatures.totalNumberOfRooms);
		assertEquals(EMPTY_NUMBER_OF_LEVEL, returnedFeatures.numberOfLevel);
		assertEquals(EMPTY_LOT_DIMENSION, returnedFeatures.lotDimension, DELTA);
		assertEquals(EMPTY_YEAR_OF_CONSTRUCTION, returnedFeatures.yearOfConstruction);
		assertEquals(EMPTY_LIVING_SPACE_AREA, returnedFeatures.livingSpaceArea, DELTA);
		assertEquals(EMPTY_BACKYARD_DIRECTION, returnedFeatures.backyardDirection);
		assertEquals(EMPTY_DESCRIPTION, returnedFeatures.description);
	}

	@Test
	public void givenANewPropertyUpdatingItsFeaturesShouldModifyItsFeatures() {
		PropertyFeatures newFeatures = new PropertyFeatures(A_NUMBER_OF_BATHROOMS, A_NUMBER_OF_BEDROOMS,
				A_TOTAL_NUMBER_OF_ROOMS, A_NUMBER_OF_LEVEL, A_LOT_DIMENSION, A_YEAR_OF_CONSTRUCTION,
				A_LIVING_SPACE_AREA, A_BACKYARD_DIRECTION, A_DESCRIPTION);
		property.updateFeatures(newFeatures);
		PropertyFeatures returnedFeatures = property.getFeatures();

		assertEquals(A_NUMBER_OF_BATHROOMS, returnedFeatures.numberOfBathrooms);
		assertEquals(A_NUMBER_OF_BEDROOMS, returnedFeatures.numberOfBedrooms);
		assertEquals(A_TOTAL_NUMBER_OF_ROOMS, returnedFeatures.totalNumberOfRooms);
		assertEquals(A_NUMBER_OF_LEVEL, returnedFeatures.numberOfLevel);
		assertEquals(A_LOT_DIMENSION, returnedFeatures.lotDimension, DELTA);
		assertEquals(A_YEAR_OF_CONSTRUCTION, returnedFeatures.yearOfConstruction);
		assertEquals(A_LIVING_SPACE_AREA, returnedFeatures.livingSpaceArea, DELTA);
		assertEquals(A_BACKYARD_DIRECTION, returnedFeatures.backyardDirection);
		assertEquals(A_DESCRIPTION, returnedFeatures.description);
	}

	@Test
	public void givenANewPropertyWhenSetCreationDateThenCreationDateIsUpdated() {
		Date creationDate = Calendar.getInstance().getTime();
		property.setCreationDate(creationDate);
		assertEquals(creationDate, property.getCreationDate());
	}

	@Test
	public void giventAPropertyOnSaleWhenCheckingIfSoldThisYearThenReturnsFalse() {
		assertFalse(property.isSoldThisYear());
	}

	@Test
	public void givenAPropertySoldOnAPassedYearWhenCheckingIfSoldThisYearThenReturnsFalse() {
		Property propertySoldOnAPassedYear = new Property(null, null, 0, null, PropertyStatus.SOLD, null,
				aPassedYearDate());
		assertFalse(propertySoldOnAPassedYear.isSoldThisYear());
	}

	private Date aPassedYearDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		Date aPassedYearDate = c.getTime();
		return aPassedYearDate;
	}

}
