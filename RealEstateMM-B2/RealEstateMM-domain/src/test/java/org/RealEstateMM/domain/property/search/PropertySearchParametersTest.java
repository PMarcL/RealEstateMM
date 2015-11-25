package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertySearchParametersTest {
	private final PropertyOrderingParameters ORDERING_PARAM = PropertyOrderingParameters.HIGHEST_PRICE_FIRST;
	private final int MIN_NUM_BEDROOMS = 2;

	private ArrayList<PropertyType> propertyTypeToFilter;
	private PropertySearchParameters searchParams;

	@Before
	public void setup() {
		propertyTypeToFilter = new ArrayList<PropertyType>();
		propertyTypeToFilter.add(PropertyType.HOUSE);
	}

	@Test
	public void givenANewPropertySearchParametersWithAllParamsThenHasAllParams() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, propertyTypeToFilter, MIN_NUM_BEDROOMS);

		assertTrue(searchParams.hasOrderingParameter());
		assertTrue(searchParams.hasPropertyTypesToFilter());
		assertTrue(searchParams.hasMinNumOfBedrooms());
	}

	@Test
	public void givenANewPropertySearchParametersWithOrderingAndPropertyTypeThenHasNoMinNumBedrooms() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, propertyTypeToFilter, 0);
		assertFalse(searchParams.hasMinNumOfBedrooms());
	}

	@Test
	public void givenANewPropertySearchParametersWithOrderingAndMinNumOfBedroomsThenHasPropertyTypesToFiles() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, new ArrayList<PropertyType>(), MIN_NUM_BEDROOMS);
		assertFalse(searchParams.hasPropertyTypesToFilter());
	}

	@Test
	public void givenANewPropertySearchParametersWithMinNumOfBedroomsAndPropertyTypesThenHasNoOrderingParam() {
		searchParams = new PropertySearchParameters(null, propertyTypeToFilter, MIN_NUM_BEDROOMS);
		assertFalse(searchParams.hasOrderingParameter());
	}
}
