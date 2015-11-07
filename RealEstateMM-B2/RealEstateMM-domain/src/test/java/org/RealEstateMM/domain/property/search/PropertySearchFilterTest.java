package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertySearchFilterTest {

	private final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";
	private final String INVALID_FILTER = null;

	private PropertySearchFilter propertyFilter;

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedFirstStringThenGetReturnsCorrespondingEnum() {
		propertyFilter = new PropertySearchFilter(RECENTLY_UPLOADED_FIRST);
		assertEquals(PropertySearchParameters.RECENTLY_UPLOADED_FIRST, propertyFilter.getParsedSearchParameter());
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedLastStringThenGetReturnsCorrespondingEnum() {
		propertyFilter = new PropertySearchFilter(RECENTLY_UPLOADED_LAST);
		assertEquals(PropertySearchParameters.RECENTLY_UPLOADED_LAST, propertyFilter.getParsedSearchParameter());
	}

	@Test(expected = InvalidFilterException.class)
	public void givenANewSearchFilterWhenInitializeWithInvalidStringThenThrowsException() {
		propertyFilter = new PropertySearchFilter(INVALID_FILTER);
		propertyFilter.getParsedSearchParameter();
	}
}
