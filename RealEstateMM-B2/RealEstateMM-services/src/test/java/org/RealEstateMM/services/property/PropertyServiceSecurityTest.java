package org.RealEstateMM.services.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.user.ForbiddenAccessException;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";

	private PropertyDTO propertyDTO;
	private PropertyAddressDTO addressDTO;
	private PropertySearchParametersDTO searchParamsDTO;
	private UserAuthorizations authorizations;
	private PropertyServiceHandler serviceHandler;
	private PropertyServiceSecurity service;
	private List<PropertyDTO> propertyList;

	@Before
	public void setup() {
		propertyDTO = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		searchParamsDTO = mock(PropertySearchParametersDTO.class);

		authorizations = mock(UserAuthorizations.class);
		userIsAuthorized();
		serviceHandler = mock(PropertyServiceHandler.class);
		propertyList = new ArrayList<PropertyDTO>();

		service = new PropertyServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenAPseudonymAndPropertyDTOWhenUploadPropertyShouldValidateIfUserIsAuthorized() throws Throwable {
		service.uploadProperty(PSEUDONYM, propertyDTO);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenUserIsAuthorizedWhenUploadPropertyShouldAskServiceToUploadProperty() throws Throwable {
		service.uploadProperty(PSEUDONYM, propertyDTO);
		verify(serviceHandler).uploadProperty(PSEUDONYM, propertyDTO);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenUploadPropertyShouldThrowException() throws Throwable {
		userIsNotAuthorized();
		service.uploadProperty(PSEUDONYM, propertyDTO);
	}

	@Test
	public void givenPseudonymWhenGetAllPropertiesShouldValidateIfUserIsAuthorized() throws Throwable {
		service.getPropertiesSearchResult(PSEUDONYM, searchParamsDTO);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.BUYER);
	}

	@Test
	public void givenUserIsAuthorizedWhenGetAllPropertiesSholdAskService() throws Throwable {
		given(service.getPropertiesSearchResult(PSEUDONYM, searchParamsDTO)).willReturn(propertyList);
		List<PropertyDTO> result = service.getPropertiesSearchResult(PSEUDONYM, searchParamsDTO);
		assertSame(propertyList, result);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenGetAllPropertiesShouldThrowException() throws Throwable {
		userIsNotAuthorized();
		service.getPropertiesSearchResult(PSEUDONYM, searchParamsDTO);
	}

	@Test
	public void givenPseudonymAndPropertyDTOWhenEditPropertyFeaturesShouldValidateIfUserIsAuthorized()
			throws Throwable {
		service.editPropertyFeatures(PSEUDONYM, propertyDTO);
		verify(authorizations).isUserAuthorized(PSEUDONYM, AccessLevel.SELLER);
	}

	@Test
	public void givenUserIsAuthorizedWhenEditPropertyFeaturesShouldAskService() throws Throwable {
		service.editPropertyFeatures(PSEUDONYM, propertyDTO);
		verify(serviceHandler).editPropertyFeatures(PSEUDONYM, propertyDTO);
	}

	@Test(expected = ForbiddenAccessException.class)
	public void givenUserIsNotAuthorizedWhenEditPropertyFeaturesShouldThrowExceptionIfNotAuthorized() throws Throwable {
		userIsNotAuthorized();
		service.editPropertyFeatures(PSEUDONYM, propertyDTO);
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
		given(service.getPropertyAtAddress(PSEUDONYM, addressDTO)).willReturn(propertyDTO);
		PropertyDTO result = service.getPropertyAtAddress(PSEUDONYM, addressDTO);
		assertEquals(propertyDTO, result);
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
