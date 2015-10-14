package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
	private static final double DELTA = 0.001;

	private Property property;

	private static final double A_PROPERTY_PRICE = 200000.00;
	private static final PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ONSALE;
	private static final String OWNER_USERNAME = "Bob";
	private static final String AN_OTHER_OWNER = "NotBob";
	private static final PropertyType A_PROPERTY_TYPE = PropertyType.HOUSE;

	private PropertyAddress propertyAddress;

	@Before
	public void setup() {
		propertyAddress = mock(PropertyAddress.class);

		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PROPERTY_PRICE, OWNER_USERNAME, A_PROPERTY_STATUS);
	}

	@Test
	public void givenAPropertyWhenVerifyingItsOwnerWithAnInvalidOwnerThenItShouldReturnFalse(){
		assertFalse(property.isOwnedBy(AN_OTHER_OWNER));
	}
	
	@Test
	public void givenAPropertyWhenVerifyingItsOwnerWithTheRightOwnerThenItShouldReturnTrue(){
		assertTrue(property.isOwnedBy(OWNER_USERNAME));
	}
	
	@Test
	public void givenANewPropertyThenItShouldHaveEmptyPropertyFeatures(){
		PropertyFeatures returnedFeatures = property.getFeatures();
		
		assertEquals(EMPTY_NUMBER_OF_BATHROOMS, returnedFeatures.numberOfBathrooms);
		assertEquals(EMPTY_NUMBER_OF_BEDROOMS, returnedFeatures.numberOfBedrooms);
		assertEquals(EMPTY_TOTAL_NUMBER_OF_ROOMS, returnedFeatures.totalNumberOfRooms);
		assertEquals(EMPTY_NUMBER_OF_LEVEL, returnedFeatures.numberOfLevel);
		assertEquals(EMPTY_LOT_DIMENSION, returnedFeatures.lotDimension, DELTA);
		assertEquals(EMPTY_YEAR_OF_CONSTRUCTION, returnedFeatures.yearOfConstruction);
		assertEquals(EMPTY_LIVING_SPACE_AREA, returnedFeatures.livingSpaceArea, DELTA);
		assertEquals(EMPTY_BACKYARD_DIRECTION, returnedFeatures.backyardDirection);
	}
	
	@Test
	public void givenANewPropertyUpdatingItsFeaturesShouldModifyItsFeatures(){
		//TODO finish
	}

}
