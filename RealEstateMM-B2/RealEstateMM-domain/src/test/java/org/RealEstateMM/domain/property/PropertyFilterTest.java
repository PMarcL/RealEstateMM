package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PropertyFilterTest {

	private PropertyRepository propertyRepository;
	private PropertyFilter propertyFilter;

	@Before
	public void setUp() throws Exception {
		propertyRepository = Mockito.mock(PropertyRepository.class);
		propertyFilter = new PropertyFilter(propertyRepository);
	}

	@Test
	public void givenAnEmptyListWhenFilterPropertiesSoldThisYearThenAnEmptyList() {
		ArrayList<Property> actual = propertyFilter.getPropertiesSoldThisYear();
		assertEquals(new ArrayList<Property>(), actual);
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

		given(propertyRepository.getAll()).willReturn(properties);

		ArrayList<Property> actual = propertyFilter.getPropertiesSoldThisYear();

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
