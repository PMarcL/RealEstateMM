package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.Search;
import org.RealEstateMM.domain.search.SearchEngine;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.dtos.SearchAssembler;
import org.RealEstateMM.services.search.dtos.SearchDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceTest {
	private final String PSEUDO = "pseudo32";
	private final String OWNER = "owner90";

	private SearchEngine searchEngine;
	private PropertyAssembler propertyAssembler;
	private Property property;
	private PropertyDTO propertyDTO;
	private PropertyAddress address;
	private PropertyAddressDTO addressDTO;
	private SearchDTO searchDTO;
	private Search search;
	private SearchAssembler searchAssembler;

	private SearchService searchService;

	@Before
	public void setup() {
		searchEngine = mock(SearchEngine.class);
		propertyAssembler = mock(PropertyAssembler.class);
		addressDTO = mock(PropertyAddressDTO.class);
		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		propertyDTO = mock(PropertyDTO.class);
		searchDTO = mock(SearchDTO.class);
		searchAssembler = mock(SearchAssembler.class);
		given(propertyAssembler.toDTO(property)).willReturn(propertyDTO);
		given(propertyAssembler.getAddressFromDTO(addressDTO)).willReturn(address);

		ServiceLocator.getInstance().registerService(SearchEngine.class, searchEngine);
		searchService = new SearchService(propertyAssembler, searchAssembler);
	}

	@After
	public void tearDown() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenGetsPropertyWithProperties() throws Throwable {
		searchService.getPropertiesFromOwner(OWNER);
		verify(searchEngine).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenConvertPropertiesWithAssembler() throws Throwable {
		given(searchEngine.getPropertiesFromOwner(OWNER)).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = searchService.getPropertiesFromOwner(OWNER);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesPropertiesWithAddress() throws Exception {
		searchService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(searchEngine).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerAndReturnsPropertyDTO() throws Exception {
		given(searchEngine.getPropertyAtAddress(address)).willReturn(property);

		PropertyDTO result = searchService.getPropertyAtAddress(PSEUDO, addressDTO);

		verify(propertyAssembler).toDTO(property);
		assertEquals(propertyDTO, result);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerToGetAddress() throws Exception {
		searchService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(propertyAssembler).getAddressFromDTO(addressDTO);
	}

	@Test
	public void whenExecuteSearchThenReturnsSearchResults() throws Exception {
		given(searchAssembler.fromDTO(searchDTO)).willReturn(search);
		given(searchEngine.executeSearch(search)).willReturn(buildPropertiesList());

		List<PropertyDTO> returnedDTOs = searchService.executeSearch(PSEUDO, searchDTO);

		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
