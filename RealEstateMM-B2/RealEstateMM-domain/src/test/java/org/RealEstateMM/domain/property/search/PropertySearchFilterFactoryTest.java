package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

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
	public void givenSearchParametersWithTypesAndBedroomsNumWhenGetSearchFilterReturnsCompositeWithAllSearchFilters() {
		given(searchParam.hasPropertyTypesToFilter()).willReturn(true);
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);

		PropertySearchFilterStrategy result = factory.getSearchFilterStrategy(searchParam);

		assertTrue(result instanceof PropertySearchFilterComposite);
	}

}
