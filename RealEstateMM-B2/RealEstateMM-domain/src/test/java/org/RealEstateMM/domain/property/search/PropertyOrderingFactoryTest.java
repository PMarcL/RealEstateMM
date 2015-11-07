package org.RealEstateMM.domain.property.search;

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
				.getOrderingStrategy(PropertySearchParameters.RECENTLY_UPLOADED_FIRST);
		assertTrue(strategy instanceof PropertyRecentlyUploadedFirst);
	}
}
