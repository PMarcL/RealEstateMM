package org.RealEstateMM.domain.search.ordering;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.ordering.DescendingAddedDateOrdering;
import org.junit.Before;
import org.junit.Test;

public class DescendingAddedDateOrderingTest {

	private Property newProperty;
	private Property oldProperty;
	private ArrayList<Property> properties;

	private DescendingAddedDateOrdering orderingStrategy;

	@Before
	public void setup() {
		orderingStrategy = new DescendingAddedDateOrdering();

		createPropertyList();
		makeNewPropertyMoreRecentThanOldProperty();
	}

	@Test
	public void givenTwoPropertiesWhenOrderingThenReturnsArraylistWithMostRecentPropertyFirst() {
		List<Property> returnedProperties = orderingStrategy.sort(properties);
		assertEquals(newProperty, returnedProperties.get(0));
		assertEquals(oldProperty, returnedProperties.get(1));
	}

	private void createPropertyList() {
		newProperty = mock(Property.class);
		oldProperty = mock(Property.class);
		properties = new ArrayList<Property>();
		properties.add(newProperty);
		properties.add(oldProperty);
	}

	private void makeNewPropertyMoreRecentThanOldProperty() {
		given(newProperty.getCreationDate()).willReturn(new Date(10000));
		given(oldProperty.getCreationDate()).willReturn(new Date(1000));
	}
}
