package org.RealEstateMM.domain.search.criterias;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.filters.PropertyTypeFilter;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.criterias.PropertyTypeCriteria;
import org.junit.Before;
import org.junit.Test;

public class PropertyTypeCriteriaTest {
	private final List<Property> PROPERTIES = new ArrayList<Property>();
	private final PropertyType TYPE_1 = PropertyType.HOUSE;
	private final PropertyType TYPE_2 = PropertyType.FARM;

	private List<Property> filteredPropertiesType1 = new ArrayList<Property>();
	private List<Property> filteredPropertiesType2 = new ArrayList<Property>();
	private Property propertyType1;
	private Property propertyType2;
	private ArrayList<PropertyType> typesToFilter;
	private PropertyTypeFilter typeFilter;
	private PropertyTypeCriteria filter;

	@Before
	public void setup() {
		typesToFilter = new ArrayList<PropertyType>();
		typeFilter = mock(PropertyTypeFilter.class);
		filter = new PropertyTypeCriteria(typesToFilter, typeFilter);

		filteredPropertiesType1.add(propertyType1);
		filteredPropertiesType2.add(propertyType2);
		given(typeFilter.filter(PROPERTIES, TYPE_1)).willReturn(filteredPropertiesType1);
		given(typeFilter.filter(PROPERTIES, TYPE_2)).willReturn(filteredPropertiesType2);
	}

	@Test
	public void givenNoPropertyTypeWhenGetFilteredThenReturnsProperties() {
		List<Property> result = filter.getFilteredProperties(PROPERTIES);

		assertEquals(PROPERTIES, result);
		verifyZeroInteractions(typeFilter);
	}

	@Test
	public void givenOnePropertyTypeWhenGetFilteredPropertiesThenUsesTypeFilter() {
		typesToFilter.add(TYPE_1);
		filter.getFilteredProperties(PROPERTIES);
		verify(typeFilter).filter(PROPERTIES, TYPE_1);
	}

	@Test
	public void givenOnePropertyTypeWhenGetFilteredPropertiesThenReturnsPropertyOfGivenType() {
		typesToFilter.add(TYPE_1);
		List<Property> result = filter.getFilteredProperties(PROPERTIES);
		assertTrue(result.contains(propertyType1));
	}

	@Test
	public void givenTwoTypesWhenGetFilteredPropertiesThenUsesTypeFilter() {
		typesToFilter.add(TYPE_1);
		typesToFilter.add(TYPE_2);

		filter.getFilteredProperties(PROPERTIES);

		verify(typeFilter).filter(PROPERTIES, TYPE_1);
		verify(typeFilter).filter(PROPERTIES, TYPE_2);
	}

	@Test
	public void givenTwoTypesWhenGetFilteredPropertiesTheReturnsListContainingFilteredProperties() {
		typesToFilter.add(TYPE_1);
		typesToFilter.add(TYPE_2);

		List<Property> result = filter.getFilteredProperties(PROPERTIES);

		assertTrue(result.contains(propertyType1));
		assertTrue(result.contains(propertyType2));
	}
}
