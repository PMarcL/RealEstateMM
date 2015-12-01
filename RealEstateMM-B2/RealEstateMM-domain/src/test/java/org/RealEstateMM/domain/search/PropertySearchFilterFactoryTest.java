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
	public void givenSearchParametersWithTypesAndBedroomsNumWhenGetSearchFilterReturnsCompositeWithAllSearchFilters() {
		given(searchParam.hasPropertyTypesToFilter()).willReturn(true);
		given(searchParam.hasMinNumOfBedrooms()).willReturn(true);

		PropertySearchFilterStrategy result = factory.getSearchFilterStrategy(searchParam);

		assertTrue(result instanceof PropertySearchFilterComposite);
		// TODO add get filters methods
	}

	// TODO add more tests for this class
}
