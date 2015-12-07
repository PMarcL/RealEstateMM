package org.RealEstateMM.domain.property.filters;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.junit.Before;
import org.junit.Test;

public class PropertyStatusFilterTest {
	private final PropertyStatus STATUS = PropertyStatus.ON_SALE;

	private PropertyStatusFilter propertyStatusFilter;

	@Before
	public void setUp() throws Exception {
		propertyStatusFilter = new PropertyStatusFilter();
	}

	@Test
	public void givenAListOfPropertiesWhenFilterOnSalePropertiesThenReturnOnlyThePropertiesWithStatusOnSale() {
		Property soldProperty = aFilteredProperty(false);
		Property propertyOnSale = aFilteredProperty(true);

		Collection<Property> properties = new ArrayList<Property>();
		properties.add(soldProperty);
		properties.add(propertyOnSale);
		int numberOfPropertiesOnSale = 1;

		Collection<Property> actual = propertyStatusFilter.filter(properties, STATUS);

		assertTrue(actual.contains(propertyOnSale));
		assertEquals(numberOfPropertiesOnSale, actual.size());
	}

	private Property aFilteredProperty(boolean isFiltered) {
		Property property = mock(Property.class);
		given(property.hasStatus(STATUS)).willReturn(isFiltered);
		return property;
	}

}
