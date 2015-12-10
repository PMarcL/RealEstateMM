package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.Search;
import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.SearchEngine;
import org.RealEstateMM.domain.search.SearchFactory;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.junit.Before;
import org.junit.Test;

public class SearchEngineTest {
	private final String OWNER = "owner90";
	private final ArrayList<Property> PROPERTIES = new ArrayList<Property>();
	private final ArrayList<Property> SEARCH_RESULTS = new ArrayList<>();
	private final PropertyOrderingType ORDER_BY = PropertyOrderingType.HIGHEST_PRICE_FIRST;
	private final ArrayList<SearchCriteria> SEARCH_CRITERIAS = new ArrayList<>();

	private PropertyRepository propertyRepository;
	private Property property;
	private PropertyAddress address;
	private SearchFactory searchFactory;
	private Search search;
	private SearchDescription searchDescription;

	private SearchEngine searchEngine;

	@Before
	public void setup() {
		propertyRepository = mock(PropertyRepository.class);
		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		given(propertyRepository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		given(propertyRepository.getAll()).willReturn(PROPERTIES);
		searchFactory = mock(SearchFactory.class);
		search = mock(Search.class);
		given(search.execute(PROPERTIES)).willReturn(SEARCH_RESULTS);
		searchDescription = mock(SearchDescription.class);
		given(searchDescription.getOrderBy()).willReturn(ORDER_BY);
		given(searchDescription.getCriterias()).willReturn(SEARCH_CRITERIAS);
		given(searchFactory.createSearch(searchDescription)).willReturn(search);

		searchEngine = new SearchEngine(searchFactory, propertyRepository);
	}

	@Test
	public void givenAnOwnerNameWhenGetPropertiesFromOwnerThenUsesRepository() {
		searchEngine.getPropertiesFromOwner(OWNER);
		verify(propertyRepository).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnerNameWhenGetPropertiesFromOwnerThenReturnsPropertiesFromRepository() {
		given(propertyRepository.getPropertiesFromOwner(OWNER)).willReturn(PROPERTIES);
		List<Property> result = searchEngine.getPropertiesFromOwner(OWNER);
		assertEquals(PROPERTIES, result);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesRepository() throws Exception {
		searchEngine.getPropertyAtAddress(address);
		verify(propertyRepository).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenReturnsPropertyGottenFromRepository() throws Exception {
		Property result = searchEngine.getPropertyAtAddress(address);
		assertEquals(property, result);
	}

	@Test(expected = PropertyNotFoundException.class)
	public void givenAnAddressWhenGetPropertyAtAddressThenThrowsExceptionIfNoPropertyIsFound() throws Exception {
		given(propertyRepository.getPropertyAtAddress(address)).willReturn(Optional.empty());
		searchEngine.getPropertyAtAddress(address);
	}

	@Test
	public void givenASearchWhenExecuteSearchThenReturnSearchResults() {
		List<Property> results = searchEngine.executeSearch(searchDescription);
		assertSame(SEARCH_RESULTS, results);
	}
}
