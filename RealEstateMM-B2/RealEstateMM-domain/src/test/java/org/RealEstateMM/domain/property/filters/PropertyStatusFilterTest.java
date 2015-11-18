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

	private PropertyStatusFilter propertyStatusFilter;

	@Before
	public void setUp() throws Exception {
		propertyStatusFilter = new PropertyStatusFilter();
	}

	@Test
	public void givenAListOfPropertiesWhenFilterOnSalePropertiesThenReturnOnlyThePropertiesWithStatusOnSale() {
		Property soldProperty = aPropertyMockWithStatus(PropertyStatus.SOLD);
		Property propertyOnSale = aPropertyMockWithStatus(PropertyStatus.ON_SALE);

		Collection<Property> properties = new ArrayList<Property>();
		properties.add(soldProperty);
		properties.add(propertyOnSale);
		int numberOfPropertiesOnSale = 1;

		Collection<Property> actual = propertyStatusFilter.filter(properties, PropertyStatus.ON_SALE);

		assertTrue(actual.contains(propertyOnSale));
		assertEquals(numberOfPropertiesOnSale, actual.size());
	}

	private Property aPropertyMockWithStatus(PropertyStatus propertyStatus) {
		Property property = mock(Property.class);
		given(property.getStatus()).willReturn(propertyStatus);
		return property;
	}

}
