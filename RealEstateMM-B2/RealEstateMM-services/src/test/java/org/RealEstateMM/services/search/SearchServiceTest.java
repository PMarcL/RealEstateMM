package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.PropertySearchEngine;
import org.RealEstateMM.domain.search.PropertySearchParameters;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceTest {
	private final String PSEUDO = "pseudo32";
	private final String OWNER = "owner90";
	private final String ORDER_BY = "recently_uploaded_last";
	private final PropertySearchParameters SEARCH_PARAM = PropertySearchParameters.RECENTLY_UPLOADED_LAST;

	private PropertySearchEngine searchEngine;
	private PropertyDTOAssembler assembler;
	private Property property;
	private PropertyDTO propertyDTO;
	private PropertyAddress address;
	private PropertyAddressDTO addressDTO;
	private SearchParametersParser searchParameterParser;

	private SearchService searchService;

	@Before
	public void setup() throws Throwable {
		searchEngine = mock(PropertySearchEngine.class);
		assembler = mock(PropertyDTOAssembler.class);
		addressDTO = mock(PropertyAddressDTO.class);
		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		propertyDTO = mock(PropertyDTO.class);
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(assembler.getAddressFromDTO(addressDTO)).willReturn(address);
		searchParameterParser = mock(SearchParametersParser.class);
		given(searchParameterParser.getParsedSearchParameter(ORDER_BY)).willReturn(SEARCH_PARAM);

		searchService = new SearchService(searchEngine, assembler, searchParameterParser);
	}

	@Test
	public void whenGetAllPropertiesThenGetsAllPropertyFromProperties() throws Throwable {
		searchService.getAllProperties(PSEUDO);
		verify(searchEngine).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesThenBuildDTOsFromPropertiesWithAssembler() throws Throwable {
		given(searchEngine.getAllProperties()).willReturn(buildPropertiesList());
		searchService.getAllProperties(PSEUDO);
		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetAllPropertiesThenReturnsDTOsOfAllProperties() throws Throwable {
		given(searchEngine.getAllProperties()).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = searchService.getAllProperties(PSEUDO);
		assertTrue(returnedDTOs.contains(propertyDTO));
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
	public void givenASearchParamWhenGetOrderedPropertiesThenUsesPropertiesWithParsedSearchParam() throws Throwable {
		searchService.getOrderedProperties(PSEUDO, ORDER_BY);
		verify(searchEngine).getOrderedProperties(SEARCH_PARAM);
	}

	@Test
	public void givenASearchParamWhenGetOrderedPropertiesThenReturnsPropertiesOrderedByProperties() throws Throwable {
		given(searchEngine.getOrderedProperties(SEARCH_PARAM)).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = searchService.getOrderedProperties(PSEUDO, ORDER_BY);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenASearchParamWhenGetOrderedPropertiesThenParseSearchParamWithParser() throws Throwable {
		searchService.getOrderedProperties(PSEUDO, ORDER_BY);
		verify(searchParameterParser).getParsedSearchParameter(ORDER_BY);
	}

	@Test(expected = InvalidSearchParameterException.class)
	public void givenAnInvalidSearchParamWhenGetOrderedPropertiesThenThrowsInvalidSearchParamException()
			throws Throwable {
		doThrow(InvalidSearchParameterException.class).when(searchParameterParser).getParsedSearchParameter(ORDER_BY);
		searchService.getOrderedProperties(PSEUDO, ORDER_BY);
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

		verify(assembler).toDTO(property);
		assertEquals(propertyDTO, result);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerToGetAddress() throws Exception {
		searchService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(assembler).getAddressFromDTO(addressDTO);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
