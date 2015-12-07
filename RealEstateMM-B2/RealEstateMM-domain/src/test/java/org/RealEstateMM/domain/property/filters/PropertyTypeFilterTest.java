package org.RealEstateMM.domain.property.filters;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class PropertyTypeFilterTest {
	private final PropertyType TYPE_TO_FILTER = PropertyType.HOUSE;

	private PropertyTypeFilter propertyTypeFilter;

	@Before
	public void setUp() throws Exception {
		propertyTypeFilter = new PropertyTypeFilter();
	}

	@Test
	public void givenAPropertyListWhenFilterTypeHouseThenReturnsOnlyHousePropertiesOftheInitialList() {
		Property aFarm = aFilteredMockProperty(false);
		Property aCommercial = aFilteredMockProperty(false);
		Property aHouse = aFilteredMockProperty(true);

		Collection<Property> properties = new ArrayList<Property>();
		properties.add(aFarm);
		properties.add(aCommercial);
		properties.add(aHouse);
		int numberOfHousesInProperties = 1;

		Collection<Property> actual = propertyTypeFilter.filter(properties, PropertyType.HOUSE);

		assertTrue(actual.contains(aHouse));
		assertEquals(numberOfHousesInProperties, actual.size());
	}

	private Property aFilteredMockProperty(boolean isFiltered) {
		Property property = mock(Property.class);
		given(property.hasType(TYPE_TO_FILTER)).willReturn(isFiltered);
		return property;
	}
}
