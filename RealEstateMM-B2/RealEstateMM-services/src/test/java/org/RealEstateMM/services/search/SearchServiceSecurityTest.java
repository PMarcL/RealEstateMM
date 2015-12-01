package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";

	private UserAuthorizations authorizations;
	private List<PropertyDTO> propertyList;
	private SearchServiceHandler serviceHandler;
	private PropertyDTO dto;
	private PropertyAddressDTO addressDTO;
	private PropertySearchParametersDTO searchParams;

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
		service.getPropertiesSearchResult(PSEUDONYM, searchParams);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenAnOwnerAndSearchParamsWhenGetPropertiesSearchResultThenShouldAskService() throws Exception {
		given(serviceHandler.getPropertiesSearchResult(PSEUDONYM, searchParams)).willReturn(propertyList);
		List<PropertyDTO> result = service.getPropertiesSearchResult(PSEUDONYM, searchParams);
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
}
