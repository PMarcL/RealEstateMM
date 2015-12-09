package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class SearchCriteriaFactoryTest {

	private static final int MIN_ROOM_NUMBER = 4;
	private static final int PRICE = 500;
	private SearchCriteriaFactory factory;

	@Before
	public void setup() {
		factory = new SearchCriteriaFactory();
	}

	@Test
	public void whenCreatePropertyTypeCriteriaThenReturnPropertyTypeCriteriaInstance() {
		List<PropertyType> propertyTypes = new ArrayList<>();
		SearchCriteria result = factory.createPropertyTypeCriteria(propertyTypes);
		assertTrue(result instanceof PropertyTypeCriteria);
	}

	@Test
	public void whenCreateMinBedroomCriteriaThenReturnMinBedroomCriteriaInstance() {
		SearchCriteria result = factory.createMinimumBedroomNumberCriteria(MIN_ROOM_NUMBER);
		assertTrue(result instanceof MinimumBedroomsNumberCriteria);
	}

	@Test
	public void whenCreateMinBathroomCriteriaThenReturnMinBathroomCriteriaInstance() {
		SearchCriteria result = factory.createMinimumBathroomNumberCriteria(MIN_ROOM_NUMBER);
		assertTrue(result instanceof MinimumBathroomsNumberCriteria);
	}

	@Test
	public void whenCreateMinPriceCriteriaThenReturnMinPriceCriteriaInstance() {
		SearchCriteria result = factory.createMinimumPriceCriteria(PRICE);
		assertTrue(result instanceof MinimumPriceCriteria);
	}

	@Test
	public void whenCreateMaxPriceCriteriaThenReturnMaxPriceCriteriaInstance() {
		SearchCriteria result = factory.createMaximumPriceCriteria(PRICE);
		assertTrue(result instanceof MaximumPriceCriteria);
	}
}
