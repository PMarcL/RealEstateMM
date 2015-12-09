package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.dtos.SearchDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class SearchServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";
	private final String SEARCH_NAME = "GoogleThis";

	private UserAuthorizations authorizations;
	private List<PropertyDTO> propertyList;
	private SearchServiceHandler serviceHandler;
	private PropertyDTO dto;
	private PropertyAddressDTO addressDTO;
	private SearchDTO searchDTO;

	private SearchServiceSecurity service;

	@Before
	public void setup() {
		dto = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		authorizations = mock(UserAuthorizations.class);
		serviceHandler = mock(SearchServiceHandler.class);
		propertyList = new ArrayList<PropertyDTO>();

		service = new SearchServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenAnOwnerAndSearchParamsWhenGetPropertiesSearchResultThenValidateUserAccess() throws Exception {
		service.executeSearch(PSEUDONYM, searchDTO);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenAnOwnerAndSearchParamsWhenGetPropertiesSearchResultThenShouldAskService() throws Exception {
		given(serviceHandler.executeSearch(PSEUDONYM, searchDTO)).willReturn(propertyList);
		List<PropertyDTO> result = service.executeSearch(PSEUDONYM, searchDTO);
		assertSame(propertyList, result);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenValidateUserAccess() throws Exception {
		service.getPropertiesFromOwner(PSEUDONYM);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenShouldAskService() throws Exception {
		given(serviceHandler.getPropertiesFromOwner(PSEUDONYM)).willReturn(propertyList);
		List<PropertyDTO> result = service.getPropertiesFromOwner(PSEUDONYM);
		assertSame(propertyList, result);
	}

	@Test
	public void givenAnOwnerAndAddressWhenGetPropertyAtAddressThenValidateUserAccess() throws Exception {
		service.getPropertyAtAddress(PSEUDONYM, addressDTO);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenAnOwnerAndAddressWhenGetPropertyAtAddressThenAskService() throws Exception {
		given(serviceHandler.getPropertyAtAddress(PSEUDONYM, addressDTO)).willReturn(dto);
		PropertyDTO result = service.getPropertyAtAddress(PSEUDONYM, addressDTO);
		assertEquals(dto, result);
	}

	@Test
	public void givenPseudoAndSearchDtoWhenSaveSearchShouldValidateUserAccessBeforeCallingRealService()
			throws Exception {
		service.saveSearch(PSEUDONYM, searchDTO);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
		inOrder.verify(serviceHandler).saveSearch(PSEUDONYM, searchDTO);
	}

	@Test
	public void givenPseudoWhenGetSavedSearchForUserShouldValidateUserAccessBeforeCallingService() throws Exception {
		service.getSavedSearchesForUser(PSEUDONYM);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
		inOrder.verify(serviceHandler).getSavedSearchesForUser(PSEUDONYM);
	}

	@Test
	public void givenPseudoWhenGetSaveSearchForUserShouldReturnServiceResult() throws Exception {
		List<String> searches = new ArrayList<>();
		given(serviceHandler.getSavedSearchesForUser(any())).willReturn(searches);

		List<String> result = service.getSavedSearchesForUser(PSEUDONYM);

		assertSame(searches, result);
	}

	@Test
	public void givenPseudoAndSearchNameWhenDeleteSearchShouldValidateUserAccessBeforeCallingService()
			throws Exception {
		service.deleteSearch(PSEUDONYM, SEARCH_NAME);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
		inOrder.verify(serviceHandler).deleteSearch(PSEUDONYM, SEARCH_NAME);
	}

	@Test
	public void givenPseudoAndSearchNameWhenGetSearchShouldValidateUserAccessBeforeCallingService() throws Exception {
		service.getSearch(PSEUDONYM, SEARCH_NAME);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
		inOrder.verify(serviceHandler).getSearch(PSEUDONYM, SEARCH_NAME);
	}

	@Test
	public void givenPseudoAndSearchNameWhenGetSearchShouldReturnServiceResult() throws Exception {
		given(serviceHandler.getSearch(any(), any())).willReturn(searchDTO);
		SearchDTO result = service.getSearch(PSEUDONYM, SEARCH_NAME);
		assertSame(searchDTO, result);
	}
}
