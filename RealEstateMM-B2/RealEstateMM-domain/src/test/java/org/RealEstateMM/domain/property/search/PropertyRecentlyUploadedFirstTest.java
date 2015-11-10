package org.RealEstateMM.domain.property.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Date;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.junit.Before;
import org.junit.Test;

public class PropertyRecentlyUploadedFirstTest {

	private PropertyRepository propertyRepository;
	private Property newProperty;
	private Property oldProperty;
	private ArrayList<Property> properties;

	private PropertyRecentlyUploadedFirst orderingStrategy;

	@Before
	public void setup() {
		orderingStrategy = new PropertyRecentlyUploadedFirst();

		createPropertyList();
		makeNewPropertyMoreRecentThanOldProperty();
		propertyRepository = mock(PropertyRepository.class);
		given(propertyRepository.getAll()).willReturn(properties);
	}

	@Test
	public void givenTwoPropertiesWhenOrderingThenReturnsArraylistWithMostRecentPropertyFirst() {
		ArrayList<Property> returnedProperties = orderingStrategy.getOrderedProperties(propertyRepository);
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
