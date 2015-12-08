package org.RealEstateMM.domain.search.ordering;

import static org.junit.Assert.*;
import org.RealEstateMM.domain.search.ordering.AscendingAddedDateOrdering;
import org.RealEstateMM.domain.search.ordering.DescendingPriceOrdering;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategy;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.RealEstateMM.domain.search.ordering.DescendingAddedDateOrdering;
import org.RealEstateMM.domain.search.ordering.AscendingPriceOrdering;
import org.junit.Before;
import org.junit.Test;

public class PropertyOrderingStrategyFactoryTest {

	private PropertyOrderingStrategyFactory factory;

	@Before
	public void setup() {
		factory = new PropertyOrderingStrategyFactory();
	}

	@Test
	public void givenOrderByRecentlyUploadedFirstWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		PropertyOrderingStrategy strategy = factory
				.createOrderingStrategy(PropertyOrderingType.RECENTLY_UPLOADED_FIRST);
		assertTrue(strategy instanceof DescendingAddedDateOrdering);
	}

	@Test
	public void givenOrderByRecentlyUploadedLastWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedLast() {
		PropertyOrderingStrategy strategy = factory.createOrderingStrategy(PropertyOrderingType.RECENTLY_UPLOADED_LAST);
		assertTrue(strategy instanceof AscendingAddedDateOrdering);
	}

	@Test
	public void givenOrderByHighestPriceFirstWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceFirst() {
		PropertyOrderingStrategy strategy = factory.createOrderingStrategy(PropertyOrderingType.HIGHEST_PRICE_FIRST);
		assertTrue(strategy instanceof DescendingPriceOrdering);
	}

	@Test
	public void givenOrderByHighestPriceLastWhenGetOrderingStrategyThenReturnsInstanceOfHighestPriceLast() {
		PropertyOrderingStrategy strategy = factory.createOrderingStrategy(PropertyOrderingType.HIGHEST_PRICE_LAST);
		assertTrue(strategy instanceof AscendingPriceOrdering);
	}

	@Test
	public void givenPropertySearchFilterNoOrderingWhenGetOrderingStrategyThenReturnsInstanceOfRecentlyUploadedFirst() {
		PropertyOrderingStrategy strategy = factory.createOrderingStrategy(PropertyOrderingType.NO_ORDERING);
		assertTrue(strategy instanceof DescendingAddedDateOrdering);
	}
}
