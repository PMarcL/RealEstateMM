package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.criterias.MinimumBedroomsNumberCriteria;
import org.junit.Before;
import org.junit.Test;

public class MinimumBedroomsNumberCriteriaTest {
	private final int MIN_BEDROOM_NUM = 2;

	private List<Property> properties;
	private Property filteredProperty;
	private Property unfilteredProperty;
	private MinimumBedroomsNumberCriteria filter;

	@Before
	public void setup() {
		filter = new MinimumBedroomsNumberCriteria(MIN_BEDROOM_NUM);
		initializePropertyList();
		given(filteredProperty.hasAtLeastNBedrooms(MIN_BEDROOM_NUM)).willReturn(true);
		given(unfilteredProperty.hasAtLeastNBedrooms(MIN_BEDROOM_NUM)).willReturn(false);
	}

	@Test
	public void givenPropertiesWhenGetFilteredPropertiesThenChecksIfEachPropertyIsFiltered() {
		filter.getFilteredProperties(properties);
		verify(filteredProperty).hasAtLeastNBedrooms(MIN_BEDROOM_NUM);
		verify(unfilteredProperty).hasAtLeastNBedrooms(MIN_BEDROOM_NUM);
	}

	@Test
	public void givenPropertiesWhenGetFilteredPropertiesThenReturnsPropertiesThatHasAtLeasNBedrooms() {
		List<Property> result = filter.getFilteredProperties(properties);
		assertTrue(result.contains(filteredProperty));
		assertFalse(result.contains(unfilteredProperty));
	}

	private void initializePropertyList() {
		filteredProperty = mock(Property.class);
		unfilteredProperty = mock(Property.class);
		properties = new ArrayList<Property>();
		properties.add(filteredProperty);
		properties.add(unfilteredProperty);
	}
}
