package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class MinimumBedroomsNumberCriteriaTest {
	private final int MIN_BEDROOM_NUM = 2;
	private List<Property> properties;
	private Property property;
	private MinimumBedroomsNumberCriteria criteria;

	@Before
	public void setup() {
		properties = new ArrayList<>();
		property = mock(Property.class);
		properties.add(property);
		criteria = new MinimumBedroomsNumberCriteria(MIN_BEDROOM_NUM);
	}

	@Test
	public void givenPropertyHasAtLeastNumberOfMinBedroomWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.hasAtLeastNBedrooms(geq(MIN_BEDROOM_NUM))).willReturn(true);
		List<Property> result = criteria.filterProperties(properties);
		assertTrue(result.contains(property));
	}

	@Test
	public void givenPropertyHasLessThanMinBedroomNumberWhenFilterPropertiesThenResultShouldNotContainProperty() {
		given(property.hasAtLeastNBedrooms(lt(MIN_BEDROOM_NUM))).willReturn(false);
		List<Property> result = criteria.filterProperties(properties);
		assertFalse(result.contains(property));
	}
}
