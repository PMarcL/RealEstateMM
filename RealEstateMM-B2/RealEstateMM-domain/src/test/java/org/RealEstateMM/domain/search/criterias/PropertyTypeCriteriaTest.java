package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class PropertyTypeCriteriaTest {
	private static final PropertyType PROPERTY_TYPE = PropertyType.COMMERCIAL;

	private List<PropertyType> propertyTypes;
	private List<Property> properties;
	private Property property;
	private PropertyTypeCriteria criteria;

	@Before
	public void setup() {
		propertyTypes = new ArrayList<>();
		propertyTypes.add(PROPERTY_TYPE);
		properties = new ArrayList<>();
		property = mock(Property.class);
		properties.add(property);

		criteria = new PropertyTypeCriteria(propertyTypes);
	}

	@Test
	public void givenPropertyTypeMatchSearchTypeWhenFilterPropertiesThenResultShouldContainProperty() {
		given(property.hasType(PROPERTY_TYPE)).willReturn(true);
		List<Property> result = criteria.filterProperties(properties);
		assertTrue(result.contains(property));
	}

	@Test
	public void givenPropertyTypeDoesntMatchSearchTypeWhenFiltPropertiesThenResultShouldNotContainsProperty() {
		given(property.hasType(any())).willReturn(false);
		List<Property> result = criteria.filterProperties(properties);
		assertFalse(result.contains(property));
	}

	@Test
	public void givenPropertyTypeWithoutTypesToFilterWhenFilterThenReturnsSameList() {
		propertyTypes = new ArrayList<>();
		criteria = new PropertyTypeCriteria(propertyTypes);

		List<Property> result = criteria.filterProperties(properties);

		assertTrue(result.contains(property));
	}
}
