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
	private static final String SEARCH_NAME_2 = "findThatProperty";
	private static final String SEARCH_NAME_1 = "findThisProperty";
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

	@Test
	public void whenFindSearchesForUserThenReturnsSearchNamesFromRepositoryForThisUser() {
		addSavedSearches();

		List<String> result = searches.findSearchesForUser(PSEUDONYM);

		assertTrue(result.contains(SEARCH_NAME_1));
		assertTrue(result.contains(SEARCH_NAME_2));
	}

	@Test
	public void givenPseudonymAndSearchNameWhenDeleteSearcheShouldRemoveSearchFromRepository() {
		searches.deleteSearch(PSEUDONYM, SEARCH_NAME_1);
		verify(repository).remove(PSEUDONYM, SEARCH_NAME_1);
	}

	@Test
	public void givenPseudonymAndSearchNameWhenGetSearchShouldGetSearchFromRepository() {
		SearchDescription search = searchDescriptionWithName(SEARCH_NAME_1);
		given(repository.getSearchWithNameForUser(PSEUDONYM, SEARCH_NAME_1)).willReturn(search);

		SearchDescription result = searches.getSearch(PSEUDONYM, SEARCH_NAME_1);

		assertSame(search, result);
	}

	private void addSavedSearches() {
		List<SearchDescription> savedSearches = new ArrayList<>();
		savedSearches.add(searchDescriptionWithName(SEARCH_NAME_1));
		savedSearches.add(searchDescriptionWithName(SEARCH_NAME_2));
		given(repository.findSearchesForUser(PSEUDONYM)).willReturn(savedSearches);
	}

	private SearchDescription searchDescriptionWithName(String searchName) {
		SearchDescription description = mock(SearchDescription.class);
		given(description.getName()).willReturn(searchName);
		return description;
	}
}
