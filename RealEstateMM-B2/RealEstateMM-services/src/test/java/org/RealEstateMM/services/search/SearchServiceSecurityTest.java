package org.RealEstateMM.services.search;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";
	private final String ORDER_BY = "recently_uploaded_last";

	private UserAuthorizations authorizations;
	private List<PropertyDTO> propertyList;
	private SearchServiceHandler serviceHandler;
	private PropertyDTO dto;
	private PropertyAddressDTO addressDTO;

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
	public void givenPseudonymWhenGetAllPropertiesShouldValidateIfUserIsAuthorized() throws Throwable {
		service.getAllProperties(PSEUDONYM);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenUserIsAuthorizedWhenGetAllPropertiesSholdAskService() throws Throwable {
		given(service.getAllProperties(PSEUDONYM)).willReturn(propertyList);
		List<PropertyDTO> result = service.getAllProperties(PSEUDONYM);
		assertSame(propertyList, result);
	}

	@Test
	public void givenPseudonymAndSearchFilterWhenGetOrderedPropertiesShouldValidateUserAccess() throws Throwable {
		service.getOrderedProperties(PSEUDONYM, ORDER_BY);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenUserIsAuthorizedWhenGetOrderedPropertiesShouldAskService() throws Throwable {
		given(serviceHandler.getOrderedProperties(PSEUDONYM, ORDER_BY)).willReturn(propertyList);
		List<PropertyDTO> result = service.getOrderedProperties(PSEUDONYM, ORDER_BY);
		assertSame(propertyList, result);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenValidateUserAccess() throws Exception {
		service.getPropertiesFromOwner(PSEUDONYM);
		verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenShouldAskService() throws Exception {
		given(service.getPropertiesFromOwner(PSEUDONYM)).willReturn(propertyList);
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
		given(service.getPropertyAtAddress(PSEUDONYM, addressDTO)).willReturn(dto);
		PropertyDTO result = service.getPropertyAtAddress(PSEUDONYM, addressDTO);
		assertEquals(dto, result);
	}
}
