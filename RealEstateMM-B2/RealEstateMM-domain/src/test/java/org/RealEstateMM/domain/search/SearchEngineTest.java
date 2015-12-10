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
import org.RealEstateMM.domain.search.SearchEngine;
import org.junit.Before;
import org.junit.Test;

public class SearchEngineTest {
	private final String OWNER = "owner90";
	private final ArrayList<Property> PROPERTIES = new ArrayList<Property>();
	private final ArrayList<Property> SEARCH_RESULTS = new ArrayList<>();

	private PropertyRepository propertyRepository;
	private Property property;
	private PropertyAddress address;
	private SearchAssembler searchAssembler;
	private Search search;
	private SearchDTO searchDTO;

	private SearchEngine searchEngine;

	@Before
	public void setup() {
		propertyRepository = mock(PropertyRepository.class);
		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		given(propertyRepository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		given(propertyRepository.getAll()).willReturn(PROPERTIES);
		searchAssembler = mock(SearchAssembler.class);
		search = mock(Search.class);
		given(search.execute(PROPERTIES)).willReturn(SEARCH_RESULTS);
		searchDTO = mock(SearchDTO.class);
		given(searchAssembler.fromDTO(searchDTO)).willReturn(search);

		searchEngine = new SearchEngine(searchAssembler, propertyRepository);
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
		List<Property> results = searchEngine.executeSearch(searchDTO);
		assertSame(SEARCH_RESULTS, results);
	}
}
