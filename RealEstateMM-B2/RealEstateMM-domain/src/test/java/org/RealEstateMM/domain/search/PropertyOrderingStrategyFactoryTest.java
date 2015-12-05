package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.search.PropertyOrderingType;
import org.RealEstateMM.domain.search.PropertyOrderingStrategy;
import org.RealEstateMM.domain.search.ordering.AscendingAddedDateOrdering;
import org.RealEstateMM.domain.search.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.search.ordering.DescendingPriceOrdering;
import org.RealEstateMM.domain.search.ordering.DescendingAddedDateOrdering;
import org.RealEstateMM.domain.search.ordering.AscendingPriceOrdering;
import org.junit.Before;
import org.junit.Test;

public class PropertyOrderingStrategyFactoryTest {

	private PropertySearchParameters searchParam;
	private PropertyOrderingStrategyFactory factory;

	@Before
	public void setup() {
		factory = new PropertyOrderingStrategyFactory();
		searchParam = mock(PropertySearchParameters.class);
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedFirstWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingType.RECENTLY_UPLOADED_FIRST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof DescendingAddedDateOrdering);
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedLastWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedLast() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingType.RECENTLY_UPLOADED_LAST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof AscendingAddedDateOrdering);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceFirstWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingType.HIGHEST_PRICE_FIRST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof DescendingPriceOrdering);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceLastWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceLast() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingType.HIGHEST_PRICE_LAST);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof AscendingPriceOrdering);
	}

	@Test
	public void givenPropertySearchFilterNoOrderingWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		given(searchParam.getOrderingParam()).willReturn(PropertyOrderingType.NO_ORDERING);
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(searchParam);
		assertTrue(strategy instanceof DescendingAddedDateOrdering);
	}
}
