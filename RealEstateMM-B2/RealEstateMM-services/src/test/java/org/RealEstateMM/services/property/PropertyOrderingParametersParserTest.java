package org.RealEstateMM.services.property;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.junit.Before;
import org.junit.Test;

public class PropertyOrderingParametersParserTest {

	private final String RECENTLY_UPLOADED_FIRST = "recently_uploaded_first";
	private final String RECENTLY_UPLOADED_LAST = "recently_uploaded_last";
	private static final String HIGHEST_PRICE_FIRST = "highest_price_first";
	private static final String HIGHEST_PRICE_LAST = "highest_price_last";
	private final String INVALID_FILTER = null;

	private PropertyOrderingParametersParser propertyFilter;

	@Before
	public void setup() {
		propertyFilter = new PropertyOrderingParametersParser();
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithRecentlyAddedLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingParameters.RECENTLY_UPLOADED_LAST,
				propertyFilter.getParsedSearchParameter(RECENTLY_UPLOADED_LAST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceFirstStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingParameters.HIGHEST_PRICE_FIRST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_FIRST));
	}

	@Test
	public void givenANewSearchFilterWhenInitializeWithHighestPriceLastStringThenGetReturnsCorrespondingEnum()
			throws Throwable {
		assertEquals(PropertyOrderingParameters.HIGHEST_PRICE_LAST,
				propertyFilter.getParsedSearchParameter(HIGHEST_PRICE_LAST));
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenANewSearchFilterWhenInitializeWithInvalidStringThenThrowsException() throws Throwable {
		propertyFilter.getParsedSearchParameter(INVALID_FILTER);
	}
}
