package org.RealEstateMM.services.search.validation;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.SearchDTO;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceAntiCorruptionTest {
	private final String PSEUDO = "pseudo32";
	private final String OWNER = "owner90";
	private final String ZIPCODE = "G6P7H7";
	private final String SEARCH_NAME = "GoogleThis";

	private PropertyAddressDTO addressDTO;
	private PropertyInformationsValidator validator;
	private SearchServiceHandler serviceHandler;
	private SearchDTO searchParams;

	private SearchServiceAntiCorruption service;

	@Before
	public void setup() {
		addressDTO = mock(PropertyAddressDTO.class);
		serviceHandler = mock(SearchServiceHandler.class);
		validator = mock(PropertyInformationsValidator.class);
		searchParams = mock(SearchDTO.class);

		service = new SearchServiceAntiCorruption(serviceHandler, validator);
		propertyDTOReturnsValidInfos();
	}

	@Test
	public void givenAddressDTOAndOwnerWhenGetPropertyAtAddressThenUsesService() throws Exception {
		service.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(serviceHandler).getPropertyAtAddress(PSEUDO, addressDTO);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAddressDTOWhenGetPropertyAtAddressWithInvalidZipCodeThenThrowsException() throws Throwable {
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(false);
		service.getPropertyAtAddress(PSEUDO, addressDTO);
	}

	@Test
	public void whenExecuteSearchThenCallsService() throws Throwable {
		service.executeSearch(PSEUDO, searchParams);
		verify(serviceHandler).executeSearch(PSEUDO, searchParams);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenCallsService() throws Throwable {
		service.getPropertiesFromOwner(OWNER);
		verify(serviceHandler).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void whenSaveSearchThenCallsService() throws Throwable {
		service.saveSearch(PSEUDO, searchParams);
		verify(serviceHandler).saveSearch(PSEUDO, searchParams);
	}

	@Test
	public void whenGetSavedSearchesForUserShouldReturnServiceAnswer() throws Throwable {
		List<String> savedSearches = new ArrayList<>();
		given(serviceHandler.getSavedSearchesForUser(PSEUDO)).willReturn(savedSearches);

		List<String> result = service.getSavedSearchesForUser(PSEUDO);

		assertSame(savedSearches, result);
	}

	@Test
	public void givenValidPseudonymAndSearchNameWhenDeleteSearchShouldCallService() throws Throwable {
		service.deleteSearch(PSEUDO, SEARCH_NAME);
		verify(serviceHandler).deleteSearch(PSEUDO, SEARCH_NAME);
	}

	@Test
	public void givenValidPseudonymAndSearchNameWhenGetSearchShouldReturnServiceResult() throws Throwable {
		given(serviceHandler.getSearch(PSEUDO, SEARCH_NAME)).willReturn(searchParams);
		SearchDTO result = service.getSearch(PSEUDO, SEARCH_NAME);
		assertSame(searchParams, result);
	}

	private void propertyDTOReturnsValidInfos() {
		given(addressDTO.getZipCode()).willReturn(ZIPCODE);
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(true);
	}
}
