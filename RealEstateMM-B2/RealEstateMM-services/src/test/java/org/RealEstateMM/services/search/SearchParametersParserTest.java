package org.RealEstateMM.services.search;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;

public class SearchParametersParserTest {

	private final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";
	private static final String HIGHEST_PRICE_FIRST = "highest_price_first";
	private static final String HIGHEST_PRICE_LAST = "highest_price_last";
	private final String INVALID_FILTER = null;

	private SearchParametersParser propertyFilter;

	@Before
	public void setup() {
		propertyFilter = new SearchParametersParser();
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingType.RECENTLY_UPLOADED_FIRST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingType.RECENTLY_UPLOADED_LAST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_LAST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingType.HIGHEST_PRICE_FIRST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingType.HIGHEST_PRICE_LAST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_LAST));
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenANewSearchFilterWhenInitializeWithInvalidStringThenThrowsException() throws Throwable {
		propertyFilter.getParsedSearchParameter(INVALID_FILTER);
	}
}
