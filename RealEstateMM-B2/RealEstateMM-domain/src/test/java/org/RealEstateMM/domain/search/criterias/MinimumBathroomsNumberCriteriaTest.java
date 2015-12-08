package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class MinimumBathroomsNumberCriteriaTest {
	private final int MIN_BATHROOM_NUM = 2;

	private List<Property> properties;
	private Property property;
	private MinimumBathroomsNumberCriteria criteria;

	@Before
	public void setup() {
		properties = new ArrayList<>();
		property = mock(Property.class);
		properties.add(property);
		criteria = new MinimumBathroomsNumberCriteria(MIN_BATHROOM_NUM);
	}

	@Test
	public void givenPropertyHasAtLeastNumberOfMinBathroomWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.hasAtLeastNBathrooms(geq(MIN_BATHROOM_NUM))).willReturn(true);
		List<Property> result = criteria.filterProperties(properties);
		assertTrue(result.contains(property));
	}

	@Test
	public void givenPropertyHasLessThanMinBathroomNumberWhenFilterPropertiesThenResultShouldNotContainProperty() {
		given(property.hasAtLeastNBathrooms(lt(MIN_BATHROOM_NUM))).willReturn(false);
		List<Property> result = criteria.filterProperties(properties);
		assertFalse(result.contains(property));
	}
}
