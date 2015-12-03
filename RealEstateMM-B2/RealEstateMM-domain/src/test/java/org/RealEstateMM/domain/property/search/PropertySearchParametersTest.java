package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.domain.search.PropertySearchParameters;

public class PropertySearchParametersTest {
	private final PropertyOrderingParameters ORDERING_PARAM = PropertyOrderingParameters.HIGHEST_PRICE_FIRST;
	private final int MIN_NUM_BEDROOMS = 2;
	private final int MIN_NUM_BATHROOMS = 2;

	private ArrayList<PropertyType> propertyTypeToFilter;
	private PropertySearchParameters searchParams;

	@Before
	public void setup() {
		propertyTypeToFilter = new ArrayList<PropertyType>();
		propertyTypeToFilter.add(PropertyType.HOUSE);
	}

	@Test
	public void givenANewPropertySearchParametersWithAllParamsThenHasAllParams() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, propertyTypeToFilter, MIN_NUM_BEDROOMS,
				MIN_NUM_BATHROOMS);

		assertTrue(searchParams.hasPropertyTypesToFilter());
		assertTrue(searchParams.hasMinNumOfBedrooms());
		assertTrue(searchParams.hasMinNumOfBathrooms());
	}

	@Test
	public void givenANewPropertySearchParametersWithOrderingAndPropertyTypeThenHasNoMinNumBedrooms() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, propertyTypeToFilter, 0, MIN_NUM_BATHROOMS);
		assertFalse(searchParams.hasMinNumOfBedrooms());
	}

	@Test
	public void givenANewPropertySearchParametersWithOrderingAndMinNumOfBedroomsThenHasNoPropertyTypesToFilter() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, new ArrayList<PropertyType>(), MIN_NUM_BEDROOMS,
				MIN_NUM_BATHROOMS);
		assertFalse(searchParams.hasPropertyTypesToFilter());
	}

	@Test
	public void givenANewPropertySearchParametersWithNoNumOfBathroomsThenHasNoMinNumOfBathrooms() {
		searchParams = new PropertySearchParameters(ORDERING_PARAM, propertyTypeToFilter, MIN_NUM_BEDROOMS, 0);
		assertFalse(searchParams.hasMinNumOfBathrooms());
	}
}
