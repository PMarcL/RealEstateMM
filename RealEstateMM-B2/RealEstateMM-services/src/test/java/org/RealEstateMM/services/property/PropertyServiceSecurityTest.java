package org.RealEstateMM.services.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.user.ForbiddenAccessException;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceSecurityTest {
	private final String ORDER_BY = "recently_uploaded_last";
	private final String PSEUDONYM = "bobby134";

	private PropertyDTO dto;
	private PropertyAddressDTO addressDTO;
	private UserAuthorizations authorizations;
	private PropertyServiceHandler serviceHandler;
	private PropertyServiceSecurity service;
	private List<PropertyDTO> propertyList;

	@Before
	public void setup() {
		dto = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		authorizations = mock(UserAuthorizations.class);
		userIsAuthorized();
		serviceHandler = mock(PropertyServiceHandler.class);
		propertyList = new ArrayList<PropertyDTO>();

		service = new PropertyServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenAPseudonymAndPropertyDTOWhenUploadPropertyShouldValidateIfUserIsAuthorized() throws Throwable {
		service.uploadProperty(PSEUDONYM, dto);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenUserIsAuthorizedWhenUploadPropertyShouldAskServiceToUploadProperty() throws Throwable {
		service.uploadProperty(PSEUDONYM, dto);
		verify(serviceHandler).uploadProperty(PSEUDONYM, dto);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenUploadPropertyShouldThrowException() throws Throwable {
		userIsNotAuthorized();
		service.uploadProperty(PSEUDONYM, dto);
	}

	@Test
	public void givenPseudonymWhenGetAllPropertiesShouldValidateIfUserIsAuthorized() throws Throwable {
		service.getAllProperties(PSEUDONYM);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenUserIsAuthorizedWhenGetAllPropertiesSholdAskService() throws Throwable {
		given(service.getAllProperties(PSEUDONYM)).willReturn(propertyList);
		List<PropertyDTO> result = service.getAllProperties(PSEUDONYM);
		assertSame(propertyList, result);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenGetAllPropertiesShouldThrowException() throws Throwable {
		userIsNotAuthorized();
		service.getAllProperties(PSEUDONYM);
	}

	@Test
	public void givenPseudonymAndPropertyDTOWhenEditPropertyFeaturesShouldValidateIfUserIsAuthorized()
			throws Throwable {
		service.editPropertyFeatures(PSEUDONYM, dto);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenUserIsAuthorizedWhenEditPropertyFeaturesShouldAskService() throws Throwable {
		service.editPropertyFeatures(PSEUDONYM, dto);
		verify(serviceHandler).editPropertyFeatures(PSEUDONYM, dto);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenEditPropertyFeaturesShouldThrowExceptionIfNotAuthorized() throws Throwable {
		userIsNotAuthorized();
		service.editPropertyFeatures(PSEUDONYM, dto);
	}

	@Test
	public void givenPseudonymAndSearchFilterWhenGetOrderedPropertiesShouldValidateUserAccess() throws Throwable {
		service.getOrderedProperties(PSEUDONYM, ORDER_BY);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenUserIsAuthorizedWhenGetOrderedPropertiesShouldAskService() throws Throwable {
		given(serviceHandler.getOrderedProperties(PSEUDONYM, ORDER_BY)).willReturn(propertyList);
		List<PropertyDTO> result = service.getOrderedProperties(PSEUDONYM, ORDER_BY);
		assertSame(propertyList, result);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenGetOrderedPropertiesShouldThrowExceptionIfNotAuthorized() throws Throwable {
		userIsNotAuthorized();
		service.getOrderedProperties(PSEUDONYM, ORDER_BY);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenValidateUserAccess() throws Exception {
		service.getPropertiesFromOwner(PSEUDONYM);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenAnOwnerWhenGetPropertiesFromOwnerThenShouldThrowExceptionIfNotAuthorized() throws Throwable {
		userIsNotAuthorized();
		service.getPropertiesFromOwner(PSEUDONYM);
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
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenAnOwnerAndAddressWhenGetPropertyAtAddressThenAskService() throws Exception {
		given(service.getPropertyAtAddress(PSEUDONYM, addressDTO)).willReturn(dto);
		PropertyDTO result = service.getPropertyAtAddress(PSEUDONYM, addressDTO);
		assertEquals(dto, result);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenAnOwnerAndAddressWhenGetPropertyAtAddressThenShouldThrowExceptionIfNotAuthorized()
			throws Throwable {
		userIsNotAuthorized();
		service.getPropertyAtAddress(PSEUDONYM, addressDTO);
	}

	private void userIsAuthorized() {
		given(authorizations.isUserAuthorized(anyString(), anyVararg())).willReturn(true);
	}

	private void userIsNotAuthorized() {
		given(authorizations.isUserAuthorized(anyString(), anyVararg())).willReturn(false);
	}
}
