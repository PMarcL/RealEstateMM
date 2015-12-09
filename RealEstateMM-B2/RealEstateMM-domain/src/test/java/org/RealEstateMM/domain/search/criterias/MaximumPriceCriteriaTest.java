package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class MaximumPriceCriteriaTest {
	private final int MAX_PRICE = 500;

	private List<Property> properties;
	private Property property;
	private MaximumPriceCriteria criteria;

	@Before
	public void setup() {
		properties = new ArrayList<>();
		property = mock(Property.class);
		properties.add(property);
		criteria = new MaximumPriceCriteria(MAX_PRICE);
	}

	@Test
	public void givenPropertyIsLessExpensiveThenWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.isLessExpensive(MAX_PRICE)).willReturn(true);
		List<Property> result = criteria.filterProperties(properties);
		assertTrue(result.contains(property));
	}

	@Test
	public void givenPropertyIsMoreExpensiveThenWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.isLessExpensive(MAX_PRICE)).willReturn(false);
		List<Property> result = criteria.filterProperties(properties);
		assertFalse(result.contains(property));
	}
}
