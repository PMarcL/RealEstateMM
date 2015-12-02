package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class PropertyFilterByBathroomsNumberTest {
	private final int MIN_BATHROOM_NUM = 2;

	private List<Property> properties;
	private Property filteredProperty;
	private Property unfilteredProperty;
	private PropertyFilterByBathroomsNumber filter;

	@Before
	public void setup() {
		filter = new PropertyFilterByBathroomsNumber(MIN_BATHROOM_NUM);
		initializePropertyList();
		given(filteredProperty.hasAtLeastNBathrooms(MIN_BATHROOM_NUM)).willReturn(true);
		given(unfilteredProperty.hasAtLeastNBathrooms(MIN_BATHROOM_NUM)).willReturn(false);
	}

	@Test
	public void givenPropertiesWhenGetFilteredPropertiesThenChecksIfEachPropertyIsFiltered() {
		filter.getFilteredProperties(properties);
		verify(filteredProperty).hasAtLeastNBathrooms(MIN_BATHROOM_NUM);
		verify(unfilteredProperty).hasAtLeastNBathrooms(MIN_BATHROOM_NUM);
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
