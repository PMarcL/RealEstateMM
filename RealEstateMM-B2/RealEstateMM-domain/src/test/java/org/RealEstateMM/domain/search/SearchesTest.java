package org.RealEstateMM.domain.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.junit.Before;
import org.junit.Test;

public class SearchesTest {
	private static final String OWNER = "bobby134";
	private static final String PSEUDONYM = "bobby135";
	private static final List<Property> SEARCH_RESULTS = new ArrayList<>();

	private SearchEngine searchEngine;
	private SearchRepository repository;
	private Searches searches;

	@Before
	public void setup() {
		searchEngine = mock(SearchEngine.class);
		repository = mock(SearchRepository.class);
		searches = new Searches(searchEngine, repository);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerShouldReturnSearchEngineResults() {
		given(searchEngine.getPropertiesFromOwner(OWNER)).willReturn(SEARCH_RESULTS);
		List<Property> result = searches.getPropertiesFromOwner(OWNER);
		assertSame(SEARCH_RESULTS, result);
	}

	@Test
	public void givenPropertyAddressWhenGetPropertyAtAddressShouldReturnSearchEngineResults() throws Exception {
		PropertyAddress address = mock(PropertyAddress.class);
		Property property = mock(Property.class);
		given(searchEngine.getPropertyAtAddress(address)).willReturn(property);

		Property result = searches.getPropertyAtAddress(address);

		assertSame(property, result);
	}

	@Test
	public void givenSearchDescriptionWhenExecuteSearchShouldReturnSearchResults() {
		SearchDescription searchDescription = mock(SearchDescription.class);
		given(searchEngine.executeSearch(searchDescription)).willReturn(SEARCH_RESULTS);

		List<Property> result = searches.executeSearch(searchDescription);

		assertSame(SEARCH_RESULTS, result);
	}

	@Test
	public void givenSearchDescriptionWhenSaveShouldPersistToSearchRespository() {
		SearchDescription searchDescription = mock(SearchDescription.class);
		searches.save(searchDescription, PSEUDONYM);
		verify(repository).persist(searchDescription, PSEUDONYM);
	}
}
