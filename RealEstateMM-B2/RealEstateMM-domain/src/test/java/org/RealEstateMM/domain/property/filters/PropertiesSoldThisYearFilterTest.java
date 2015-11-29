package org.RealEstateMM.domain.property.filters;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class PropertiesSoldThisYearFilterTest {

	private PropertiesSoldThisYearFilter propertiesSoldThisYearFilter;

	@Before
	public void setUp() {
		propertiesSoldThisYearFilter = new PropertiesSoldThisYearFilter();
	}

	@Test
	public void givenAnEmptyListWhenFilterPropertiesSoldThisYearThenAnEmptyList() {
		Collection<Property> actual = propertiesSoldThisYearFilter.getPropertiesSoldThisYear(new ArrayList<Property>());
		assertEquals(new ArrayList<Property>(), new ArrayList<Property>(actual));
	}

	@Test
	public void givenAListOfPropertiesWhenGetPropertiesSoldThisYearThenReturnsThePropertiesSoldThisYear() {
		Property propertySoldThisYear1 = aPropertyMockReturningIfSoldThisYear(true);
		Property propertySoldThisYear2 = aPropertyMockReturningIfSoldThisYear(true);
		Property propertySoldInAPassedYear = aPropertyMockReturningIfSoldThisYear(false);

		ArrayList<Property> properties = new ArrayList<>();
		properties.add(propertySoldThisYear1);
		properties.add(propertySoldInAPassedYear);
		properties.add(propertySoldThisYear2);
		int numberOfPropertiesSoldThisYear = 2;

		Collection<Property> actual = propertiesSoldThisYearFilter.getPropertiesSoldThisYear(properties);

		assertTrue(actual.contains(propertySoldThisYear1));
		assertTrue(actual.contains(propertySoldThisYear2));
		assertEquals(numberOfPropertiesSoldThisYear, actual.size());
	}

	private Property aPropertyMockReturningIfSoldThisYear(boolean isSoldThisYear) {
		Property property = mock(Property.class);
		given(property.isSoldThisYear()).willReturn(isSoldThisYear);
		return property;
	}
}
