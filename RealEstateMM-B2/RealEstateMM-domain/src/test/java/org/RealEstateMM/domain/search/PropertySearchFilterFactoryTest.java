package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.search.PropertySearchFilterComposite;
import org.RealEstateMM.domain.search.PropertySearchFilterFactory;
import org.RealEstateMM.domain.search.PropertySearchFilterStrategy;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchFilterFactoryTest {

	private PropertySearchParameters searchParam;
	private PropertySearchFilterFactory factory;

	@Before
	public void setup() {
		factory = new PropertySearchFilterFactory();

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
	}

	@Test
	public void givenSearchParametersWithBedroomNumWhenGetSearchFilterReturnsCompositeWithPropertyBedroomFilter() {
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByBedroomsNumber);
	}

	@Test
	public void givenSearchParametersWithPropertyTypeAndBedroomWhenGetSearchFilterReturnsCompositeWithAllFilters() {
		given(searchParam.hasPropertyTypesToFilter()).willReturn(true);
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);

		PropertySearchFilterComposite result = (PropertySearchFilterComposite) factory
				.getSearchFilterStrategy(searchParam);

		assertTrue(result.get(0) instanceof PropertyFilterByTypes);
		assertTrue(result.get(1) instanceof PropertyFilterByBedroomsNumber);
	}
}
