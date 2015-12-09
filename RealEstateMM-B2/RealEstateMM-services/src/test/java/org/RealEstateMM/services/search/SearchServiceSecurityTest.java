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
}
