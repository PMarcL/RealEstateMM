package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.junit.Before;
import org.junit.Test;

public class PropertyWithHighestPriceFirstTest {

	private static final Double HIGH_PRICE = 200000.00;
	private static final Double LOW_PRICE = 100000.00;

	private PropertyRepository propertyRepository;
	private Property mostExpensiveProperty;
	private Property leastExpensiveProperty;
	private ArrayList<Property> properties;

	private PropertyWithHighestPriceFirst orderingStrategy;

	@Before
	public void setup() {
		orderingStrategy = new PropertyWithHighestPriceFirst();

		createPropertyList();
		makeNewPropertyMoreRecentThanOldProperty();
		propertyRepository = mock(PropertyRepository.class);
		given(propertyRepository.getAll()).willReturn(properties);
	}

	@Test
	public void givenTwoPropertiesWhenOrderingThenReturnsArraylistWithHighestPricePropertyFirst() {
		ArrayList<Property> returnedProperties = orderingStrategy.getOrderedProperties(propertyRepository);
		assertEquals(mostExpensiveProperty, returnedProperties.get(0));
		assertEquals(leastExpensiveProperty, returnedProperties.get(1));
	}

	private void createPropertyList() {
		mostExpensiveProperty = mock(Property.class);
		leastExpensiveProperty = mock(Property.class);
		properties = new ArrayList<Property>();
		properties.add(mostExpensiveProperty);
		properties.add(leastExpensiveProperty);
	}

	private void makeNewPropertyMoreRecentThanOldProperty() {
		given(mostExpensiveProperty.getPrice()).willReturn(HIGH_PRICE);
		given(leastExpensiveProperty.getPrice()).willReturn(LOW_PRICE);
	}
}