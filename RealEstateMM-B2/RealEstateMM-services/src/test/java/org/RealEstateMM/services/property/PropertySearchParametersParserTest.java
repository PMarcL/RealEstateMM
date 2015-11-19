package org.RealEstateMM.services.property;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchParametersParserTest {

	private final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";
	private static final String HIGHEST_PRICE_FIRST = "highest_price_first";
	private static final String HIGHEST_PRICE_LAST = "highest_price_last";
	private final String INVALID_FILTER = null;

	private PropertySearchParametersParser propertyFilter;

	@Before
	public void setup() {
		propertyFilter = new PropertySearchParametersParser();
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertySearchParameters.RECENTLY_UPLOADED_FIRST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertySearchParameters.RECENTLY_UPLOADED_LAST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_LAST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertySearchParameters.HIGHEST_PRICE_FIRST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertySearchParameters.HIGHEST_PRICE_LAST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_LAST));
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenANewSearchFilterWhenInitializeWithInvalidStringThenThrowsException() throws Throwable {
		propertyFilter.getParsedSearchParameter(INVALID_FILTER);
	}
}
