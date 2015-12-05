package org.RealEstateMM.domain.search.ordering;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.ordering.AscendingPriceOrdering;
import org.junit.Before;
import org.junit.Test;

public class AscendingPriceOrderingTest {

	private static final Double HIGH_PRICE = 200000.00;
	private static final Double LOW_PRICE = 100000.00;

	private Property expensiveProperty;
	private Property cheapProperty;
	private ArrayList<Property> properties;

	private AscendingPriceOrdering orderingStrategy;

	@Before
	public void setup() {
		orderingStrategy = new AscendingPriceOrdering();

		createPropertyList();
		makeExpensivePropertyMoreExpensizeThanCheapProperty();
	}

	@Test
	public void givenTwoPropertiesWhenOrderingThenReturnsArraylistWithHighestPricePropertyLast() {
		List<Property> returnedProperties = orderingStrategy.sort(properties);
		assertEquals(cheapProperty, returnedProperties.get(0));
		assertEquals(expensiveProperty, returnedProperties.get(1));
	}

	private void createPropertyList() {
		expensiveProperty = mock(Property.class);
		cheapProperty = mock(Property.class);
		properties = new ArrayList<Property>();
		properties.add(expensiveProperty);
		properties.add(cheapProperty);
	}

	private void makeExpensivePropertyMoreExpensizeThanCheapProperty() {
		given(expensiveProperty.getPrice()).willReturn(HIGH_PRICE);
		given(cheapProperty.getPrice()).willReturn(LOW_PRICE);
	}
}
