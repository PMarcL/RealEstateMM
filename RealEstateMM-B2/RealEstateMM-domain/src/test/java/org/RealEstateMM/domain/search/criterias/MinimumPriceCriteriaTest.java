package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class MinimumPriceCriteriaTest {
	private final int MIN_PRICE = 500;

	private List<Property> properties;
	private Property property;
	private MinimumPriceCriteria criteria;

	@Before
	public void setup() {
		properties = new ArrayList<>();
		property = mock(Property.class);
		properties.add(property);
		criteria = new MinimumPriceCriteria(MIN_PRICE);
	}

	@Test
	public void givenPropertyIsMoreExpensiveThenWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.isMoreExpensive(MIN_PRICE)).willReturn(true);
		List<Property> result = criteria.filterProperties(properties);
		assertTrue(result.contains(property));
	}

	@Test
	public void givenPropertyIsLessExpensiveThenWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.isMoreExpensive(MIN_PRICE)).willReturn(false);
		List<Property> result = criteria.filterProperties(properties);
		assertFalse(result.contains(property));
	}
}
