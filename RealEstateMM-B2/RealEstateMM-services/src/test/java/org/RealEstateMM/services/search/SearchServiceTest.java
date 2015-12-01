package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.PropertySearchEngine;
import org.RealEstateMM.domain.search.PropertySearchParameters;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTOAssembler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceTest {
	private final String PSEUDO = "pseudo32";
	private final String OWNER = "owner90";

	private PropertySearchEngine searchEngine;
	private PropertyAssembler assembler;
	private Property property;
	private PropertyDTO propertyDTO;
	private PropertyAddress address;
	private PropertyAddressDTO addressDTO;
	private PropertySearchParametersDTO searchParamsDTO;
	private PropertySearchParameters searchParams;
	private PropertySearchParametersDTOAssembler searchParamAssembler;

	private SearchService searchService;

	@Before
	public void setup() throws Throwable {
		searchEngine = mock(PropertySearchEngine.class);
		assembler = mock(PropertyAssembler.class);
		addressDTO = mock(PropertyAddressDTO.class);
		address = mock(PropertyAddress.class);
		property = mock(Property.class);
		propertyDTO = mock(PropertyDTO.class);
		searchParamsDTO = mock(PropertySearchParametersDTO.class);
		searchParamAssembler = mock(PropertySearchParametersDTOAssembler.class);
		searchParams = mock(PropertySearchParameters.class);
		given(searchParamAssembler.fromDTO(searchParamsDTO)).willReturn(searchParams);
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(assembler.getAddressFromDTO(addressDTO)).willReturn(address);

		ServiceLocator.getInstance().registerService(PropertySearchEngine.class, searchEngine);
		searchService = new SearchService(assembler, searchParamAssembler);
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

		verify(assembler).toDTO(property);
		assertEquals(propertyDTO, result);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerToGetAddress() throws Exception {
		searchService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(assembler).getAddressFromDTO(addressDTO);
	}

	@Test
	public void whenGetPropertiesSearchResultsThenUsesAssemblerToBuildSearchParameters() throws Exception {
		searchService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		verify(searchParamAssembler).fromDTO(searchParamsDTO);
	}

	@Test
	public void whenGetPropertiesSearchResultsThenGetPropertiesSearchFromProperties() throws Exception {
		searchService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		verify(searchEngine).getPropertiesSearchResults(searchParams);
	}

	@Test
	public void whenGetPropertiesSearchResultsThenBuildDTOsFromPropertiesWithAssembler() throws Exception {
		given(searchEngine.getPropertiesSearchResults(searchParams)).willReturn(buildPropertiesList());
		searchService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetPropertiesSearchResultsThenReturnsDTOsOfAllProperties() throws Exception {
		given(searchEngine.getPropertiesSearchResults(searchParams)).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = searchService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
