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

	private PropertyTypeFilter propertyTypeFilter;

	@Before
	public void setUp() throws Exception {
		propertyTypeFilter = new PropertyTypeFilter();
	}

	@Test
	public void givenAPropertyListWhenFilterTypeHouseThenReturnsOnlyHousePropertiesOftheInitialList() {
		Property aFarm = aMockPropertyWithType(PropertyType.FARM);
		Property aCommercial = aMockPropertyWithType(PropertyType.COMMERCIAL);
		Property aHouse = aMockPropertyWithType(PropertyType.HOUSE);

		Collection<Property> properties = new ArrayList<Property>();
		properties.add(aFarm);
		properties.add(aCommercial);
		properties.add(aHouse);
		int numberOfHousesInProperties = 1;

		Collection<Property> actual = propertyTypeFilter.filter(properties, PropertyType.HOUSE);

		assertTrue(actual.contains(aHouse));
		assertEquals(numberOfHousesInProperties, actual.size());
	}

	private Property aMockPropertyWithType(PropertyType type) {
		Property property = mock(Property.class);
		given(property.getType()).willReturn(type);
		return property;
	}
}
