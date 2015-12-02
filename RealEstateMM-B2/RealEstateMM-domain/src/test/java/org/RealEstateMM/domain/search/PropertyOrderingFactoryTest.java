package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

public class PropertyOrderingFactoryTest {

	private PropertySearchParameters searchParam;
	private PropertyOrderingFactory factory;

	@Before
	public void setup() {
		factory = new PropertyOrderingFactory();
		searchParam = mock(PropertySearchParameters.class);
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedFirstWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof PropertyRecentlyUploadedFirst);
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedLastWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedLast() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingParameters.RECENTLY_UPLOADED_LAST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof PropertyRecentlyUploadedLast);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceFirstWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingParameters.HIGHEST_PRICE_FIRST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof PropertyWithHighestPriceFirst);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceLastWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceLast() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingParameters.HIGHEST_PRICE_LAST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof PropertyWithHighestPriceLast);
	}

	@Test
	public void givenPropertySearchFilterNoOrderingWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingParameters.NO_ORDERING);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof PropertyRecentlyUploadedFirst);
	}
}
