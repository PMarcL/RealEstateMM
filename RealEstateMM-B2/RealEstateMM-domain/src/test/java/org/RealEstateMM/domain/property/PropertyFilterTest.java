package org.RealEstateMM.domain.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Date;

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
	public void givenAnEmptyListWhenGetNumberOfPropertiesSoldThisYearThenReturnsZero() {
		ArrayList<Property> actual = propertyFilter.getNumberOfNumberOfPropertiesSoldThisYear();
		assertEquals(new ArrayList<Property>(), actual);
	}

	@Test
	public void givenAnEmptyListWhenGetNumberOfPropertiesSoldThisYearThenReturnsTheAmountOfPropertySoldThisYear() {
		ArrayList<Property> properties = new ArrayList<>();
		properties.add(mock(Property.class));
		properties.add(mock(Property.class));
		properties.add(mock(Property.class));
		given(properties.get(0).getSaleDate()).willReturn(new Date());
		given(properties.get(1).getSaleDate()).willReturn(new Date());

		// what to do with the almost completely deprecated Date class
		Date A_PASSED_YEAR_DATE = new Date(1990, 1, 1);
		given(properties.get(2).getSaleDate()).willReturn(A_PASSED_YEAR_DATE);

		given(propertyRepository.getAllProperties()).willReturn(properties);
		assertEquals(, propertyFilter.getNumberOfNumberOfPropertiesSoldThisYear());
	}

}
