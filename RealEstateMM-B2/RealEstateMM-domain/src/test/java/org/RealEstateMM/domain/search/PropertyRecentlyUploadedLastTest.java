package org.RealEstateMM.domain.search;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.search.PropertyRecentlyUploadedLast;
import org.junit.Before;
import org.junit.Test;

public class PropertyRecentlyUploadedLastTest {
	private PropertyRepository propertyRepository;
	private Property newProperty;
	private Property oldProperty;
	private ArrayList<Property> properties;

	private PropertyRecentlyUploadedLast orderingStrategy;

	@Before
	public void setup() {
		orderingStrategy = new PropertyRecentlyUploadedLast();

		createPropertyList();
		makeNewPropertyMoreRecentThanOldProperty();
		propertyRepository = mock(PropertyRepository.class);
		given(propertyRepository.getAll()).willReturn(properties);
	}

	@Test
	public void givenTwoPropertiesWhenOrderingThenReturnsArraylistWithMostRecentPropertyLast() {
		ArrayList<Property> returnedProperties = orderingStrategy.getOrderedProperties(propertyRepository);
		assertEquals(oldProperty, returnedProperties.get(0));
		assertEquals(newProperty, returnedProperties.get(1));
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
