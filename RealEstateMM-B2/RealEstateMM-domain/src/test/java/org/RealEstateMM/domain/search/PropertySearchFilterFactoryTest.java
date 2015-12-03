package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.search.PropertySearchFilterComposite;
import org.RealEstateMM.domain.search.PropertySearchFilterFactory;
import org.RealEstateMM.domain.search.PropertySearchFilterStrategy;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchFilterFactoryTest {

	private PropertySearchParameters searchParam;
	private PropertyFilterFactory propertyFilterFactory;
	private PropertySearchFilterFactory factory;

	@Before
	public void setup() {
		propertyFilterFactory = mock(PropertyFilterFactory.class);
		factory = new PropertySearchFilterFactory(propertyFilterFactory);

		searchParam = mock(PropertySearchParameters.class);
	}

	@Test
	public void givenSearchParametersWhenGetSearchFilterReturnsComposite() {
		PropertySearchFilterStrategy result = factory.getSearchFilterStrategy(searchParam);
		assertTrue(result instanceof PropertySearchFilterComposite);
	}

	@Test
	public void givenSearchParametersWithPropertyTypeWhenGetSearchFilterReturnsCompositeWithPropertyTypeFilter() {
		given(searchParam.hasPropertyTypesToFilter()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByTypes);
		verify(propertyFilterFactory).createPropertyTypeFilter();
	}

	@Test
	public void givenSearchParametersWithBedroomNumWhenGetSearchFilterReturnsCompositeWithPropertyBedroomFilter() {
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByBedroomsNumber);
	}

	@Test
	public void givenSearchParameterWithBathroomNumWhenGetSearchFilterREturnsCompositeWithPropertyBathroomFilter() {
		given(searchParam.hasMinNumOfBathrooms()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByBathroomsNumber);
	}

	@Test
	public void givenSearchParametersWithPropertyTypeAndBedroomWhenGetSearchFilterReturnsCompositeWithAllFilters() {
		given(searchParam.hasPropertyTypesToFilter()).willReturn(true);
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);
		given(searchParam.hasMinNumOfBathrooms()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByTypes);
		assertTrue(result.get(1) instanceof PropertyFilterByBedroomsNumber);
		assertTrue(result.get(2) instanceof PropertyFilterByBathroomsNumber);
	}
}
