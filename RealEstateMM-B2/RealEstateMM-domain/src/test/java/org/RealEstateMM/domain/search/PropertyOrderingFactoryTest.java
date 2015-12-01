package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PropertyOrderingFactoryTest {

	private PropertyOrderingFactory factory;

	@Before
	public void setup() {
		factory = new PropertyOrderingFactory();
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedFirstWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		PropertyOrderingStrategy strategy = factory
				.getOrderingStrategy(PropertyOrderingParameters.RECENTLY_UPLOADED_FIRST);
		assertTrue(strategy instanceof PropertyRecentlyUploadedFirst);
	}

	@Test
	public void givenPropertySearchFilterRecentlyUploadedLastWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedLast() {
		PropertyOrderingStrategy strategy = factory
				.getOrderingStrategy(PropertyOrderingParameters.RECENTLY_UPLOADED_LAST);
		assertTrue(strategy instanceof PropertyRecentlyUploadedLast);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceFirstWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceFirst() {
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(PropertyOrderingParameters.HIGHEST_PRICE_FIRST);
		assertTrue(strategy instanceof PropertyWithHighestPriceFirst);
	}

	@Test
	public void givenPropertySearchFilterHighestPriceLastWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceLast() {
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(PropertyOrderingParameters.HIGHEST_PRICE_LAST);
		assertTrue(strategy instanceof PropertyWithHighestPriceLast);
	}

	@Test
	public void givenPropertySearchFilterNoOrderingWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		PropertyOrderingStrategy strategy = factory.getOrderingStrategy(PropertyOrderingParameters.NO_ORDERING);
		assertTrue(strategy instanceof PropertyRecentlyUploadedFirst);
	}
}
