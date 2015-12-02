package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.junit.Before;
import org.junit.Test;

public class PropertySearchFilterCompositeTest {

	private final List<Property> PROPERTIES = new ArrayList<Property>();
	private final List<Property> FILTERED_PROPERTIES_1 = new ArrayList<Property>();
	private final List<Property> FILTERED_PROPERTIES_2 = new ArrayList<Property>();

	private PropertySearchFilterStrategy filter1;
	private PropertySearchFilterStrategy filter2;
	private PropertySearchFilterComposite searchFilterComposite;

	@Before
	public void setup() {
		searchFilterComposite = new PropertySearchFilterComposite();

		filter1 = mock(PropertySearchFilterStrategy.class);
		filter2 = mock(PropertySearchFilterStrategy.class);
		given(filter1.getFilteredProperties(PROPERTIES)).willReturn(FILTERED_PROPERTIES_1);
		given(filter2.getFilteredProperties(PROPERTIES)).willReturn(FILTERED_PROPERTIES_2);
	}

	@Test
	public void givenANewSearchFilterCompositeWhenAddingFilterThenContainsFilter() {
		searchFilterComposite.add(filter1);
		assertEquals(filter1, searchFilterComposite.get(0));
	}

	@Test
	public void givenANewSearchFilterCompositeWhenAddingFilterThenUsesFilterWhenGetFilteredProperties() {
		searchFilterComposite.add(filter1);
		searchFilterComposite.getFilteredProperties(PROPERTIES);
		verify(filter1).getFilteredProperties(PROPERTIES);
	}

	@Test
	public void givenANewSearchFilterCompositeWhenAddingFilterThenUsesFilterAndReturnsFilteredProperties() {
		searchFilterComposite.add(filter1);
		List<Property> result = searchFilterComposite.getFilteredProperties(PROPERTIES);
		assertEquals(FILTERED_PROPERTIES_1, result);
	}

	@Test
	public void givenANewSearchFilterCompositeWhenGetFilteredPropertiesWithoutFilterThenReturnsUnfilteredProperties() {
		List<Property> result = searchFilterComposite.getFilteredProperties(PROPERTIES);
		assertEquals(PROPERTIES, result);
	}

	@Test
	public void givenANewSearchFilterCompositeWhenAddingMultipleFiltersThenUsesAllFilterWhenGetFilteredProperties() {
		searchFilterComposite.add(filter1);
		searchFilterComposite.add(filter2);

		searchFilterComposite.getFilteredProperties(PROPERTIES);

		verify(filter1).getFilteredProperties(PROPERTIES);
		verify(filter2).getFilteredProperties(FILTERED_PROPERTIES_1);
	}

	@Test
	public void givenASearchFilterCompositeWithMultipleFiltersWhenGetFilteredPropertiesThenReturnsPropertiesFromLastFilter() {
		searchFilterComposite.add(filter1);
		searchFilterComposite.add(filter2);

		List<Property> result = searchFilterComposite.getFilteredProperties(PROPERTIES);

		assertEquals(FILTERED_PROPERTIES_2, result);
	}
}
