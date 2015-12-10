package org.RealEstateMM.persistence.xml.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.SearchDTO;
import org.RealEstateMM.domain.search.SearchNotFoundException;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class XmlSearchRespositoryTest {
	private static final String PSEUDONYM = "bobby134";
	private static final String SEARCH_NAME = "GoogleThis";

	private XmlMarshaller marshaller;
	private XmlSearchCollection searchCollection;
	private List<XmlSearchDescription> xmlSearches;
	private XmlSearchDescription xmlSearch;
	private XmlSearchAssembler assembler;
	private SearchDTO searchDescription;

	private XmlSearchRespository repository;

	@Before
	public void setup() {
		setupMocks();
		repository = new XmlSearchRespository(marshaller, assembler);
	}

	@Test
	public void unmarshallSearchesCollectionWhenCreated() {
		List<SearchDTO> searches = repository.getSearchesForUser(PSEUDONYM);
		assertTrue(searches.contains(searchDescription));
	}

	@Test
	public void givenEmptyXmlFileWhenCreatedShouldInitializeEmptySearchCollection() {
		given(marshaller.unmarshal(any())).willThrow(new EmptyXmlFileException());

		repository = new XmlSearchRespository(marshaller, assembler);

		List<SearchDTO> searches = repository.getSearchesForUser(PSEUDONYM);
		assertFalse(searches.contains(searchDescription));
	}

	@Test
	public void givenXmlMarshallingErrorWhenCreatedShouldInitializeEmptySearchCollection() {
		given(marshaller.unmarshal(any())).willThrow(new XmlMarshallingException(null));

		repository = new XmlSearchRespository(marshaller, assembler);

		List<SearchDTO> searches = repository.getSearchesForUser(PSEUDONYM);
		assertFalse(searches.contains(searchDescription));
	}

	@Test
	public void givenSearchDoesNotExistWhenAddSearchShouldAddAssembledSearchToCache() {
		given(searchCollection.contains(PSEUDONYM, SEARCH_NAME)).willReturn(false);
		repository.addSearch(searchDescription, PSEUDONYM);
		verify(searchCollection).add(xmlSearch);
	}

	@Test
	public void givenSearchDoesNotExistWhenAddSearchShouldMarshallCacheAfterAddingNewSearch() {
		given(searchCollection.contains(PSEUDONYM, SEARCH_NAME)).willReturn(false);

		repository.addSearch(searchDescription, PSEUDONYM);

		InOrder inOrder = inOrder(searchCollection, marshaller);
		inOrder.verify(searchCollection).add(any());
		inOrder.verify(marshaller).marshal(XmlSearchCollection.class, searchCollection);
	}

	@Test
	public void givenSearchAlreadyExistWhenAddSearchShouldOverwritePreviousVersion() {
		given(searchCollection.contains(any(), any())).willReturn(true);

		repository.addSearch(searchDescription, PSEUDONYM);

		InOrder inOrder = inOrder(searchCollection);
		inOrder.verify(searchCollection).remove(PSEUDONYM, SEARCH_NAME);
		inOrder.verify(searchCollection).add(xmlSearch);
	}

	@Test
	public void whenRemoveSearchShouldRemoveFromCacheBeforeMarshalling() {
		repository.remove(PSEUDONYM, SEARCH_NAME);

		InOrder inOrder = inOrder(searchCollection, marshaller);
		inOrder.verify(searchCollection).remove(PSEUDONYM, SEARCH_NAME);
		inOrder.verify(marshaller).marshal(XmlSearchCollection.class, searchCollection);
	}

	@Test
	public void givenSearchExistWhenGetSearcheWithNameForUserShouldReturnSearch() throws Throwable {
		SearchDTO result = repository.getSearchWithNameForUser(PSEUDONYM, SEARCH_NAME);
		assertSame(searchDescription, result);
	}

	@Test(expected = SearchNotFoundException.class)
	public void givenSearchDoesNotExistWhenGetSearchWithNameForUserShouldThrowException() throws Throwable {
		given(searchCollection.getSearchesForUser(any())).willReturn(new ArrayList<>());
		repository.getSearchWithNameForUser(PSEUDONYM, SEARCH_NAME);
	}

	private void setupMocks() {
		marshaller = mock(XmlMarshaller.class);
		searchCollection = mock(XmlSearchCollection.class);
		given(marshaller.unmarshal(XmlSearchCollection.class)).willReturn(searchCollection);
		xmlSearches = new ArrayList<>();
		xmlSearch = mock(XmlSearchDescription.class);
		given(xmlSearch.getName()).willReturn(SEARCH_NAME);
		xmlSearches.add(xmlSearch);
		given(searchCollection.getSearchesForUser(PSEUDONYM)).willReturn(xmlSearches);
		assembler = mock(XmlSearchAssembler.class);
		searchDescription = mock(SearchDTO.class);
		given(searchDescription.getName()).willReturn(SEARCH_NAME);
		given(assembler.toSearchDTO(xmlSearch)).willReturn(searchDescription);
		given(assembler.fromSearchDTO(searchDescription, PSEUDONYM)).willReturn(xmlSearch);
	}
}
