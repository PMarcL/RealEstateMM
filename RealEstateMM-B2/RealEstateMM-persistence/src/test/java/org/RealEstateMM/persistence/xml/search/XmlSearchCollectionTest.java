package org.RealEstateMM.persistence.xml.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XmlSearchCollectionTest {
	private static final String PSEUDONYM = "bobby134";
	private static final String ANOTHER_PSEUDONYM = "bobby135";
	private static final String SEARCH_NAME = "GoogleThat";
	private static final String ANOTHER_SEARCH_NAME = "GoogleThis";

	private XmlSearchCollection collection;
	private XmlSearchDescription search;

	@Before
	public void setup() {
		collection = new XmlSearchCollection();
		fillCollection();
	}

	@Test
	public void whenGetSearchesForUserShouldReturnAllSearchesForTheUser() {
		List<XmlSearchDescription> result = collection.getSearchesForUser(PSEUDONYM);
		assertTrue(result.contains(search));
	}

	@Test
	public void givenNewSearchWhenAddShouldContainsSearch() {
		XmlSearchDescription newSearch = mock(XmlSearchDescription.class);
		given(newSearch.getName()).willReturn(ANOTHER_SEARCH_NAME);
		given(newSearch.getPseudonym()).willReturn(ANOTHER_PSEUDONYM);

		collection.add(newSearch);

		assertTrue(collection.contains(ANOTHER_PSEUDONYM, ANOTHER_SEARCH_NAME));
	}

	@Test
	public void givenExistingSearchWhenRemoveShouldNotContainsSearch() {
		collection.remove(PSEUDONYM, SEARCH_NAME);
		assertFalse(collection.contains(PSEUDONYM, SEARCH_NAME));
	}

	private void fillCollection() {
		search = mock(XmlSearchDescription.class);
		given(search.getPseudonym()).willReturn(PSEUDONYM);
		given(search.getName()).willReturn(SEARCH_NAME);

		ArrayList<XmlSearchDescription> searches = new ArrayList<>();
		searches.add(search);
		collection.setSearches(searches);
	}
}
