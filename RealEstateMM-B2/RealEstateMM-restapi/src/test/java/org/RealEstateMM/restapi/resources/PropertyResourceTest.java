package org.RealEstateMM.restapi.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import java.util.ArrayList;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.restapi.resources.PropertyResource;
import org.RealEstateMM.restapi.resources.PropertySearchParametersFactory;
import org.RealEstateMM.services.property.InvalidSearchParameterException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.validation.InvalidPropertyInformationException;
import org.RealEstateMM.services.user.ForbiddenAccessException;

public class PropertyResourceTest {

	private final String OWNER = "owner90";
	private final String TOKEN = "token";

	private PropertyResource propertyResource;
	private PropertyDTO propertyDTO;
	private PropertyAddressDTO addressDTO;
	private PropertyServiceHandler service;
	private SessionService sessionService;
	private UriInfo searchParam;
	private PropertySearchParametersDTO searchParamDTO;
	private PropertySearchParametersFactory searchParamFactory;

	@Before
	public void setup() throws Throwable {
		service = mock(PropertyServiceHandler.class);
		sessionService = mock(SessionService.class);
		searchParamFactory = mock(PropertySearchParametersFactory.class);
		propertyResource = new PropertyResource(service, sessionService, searchParamFactory);

		searchParam = mock(UriInfo.class);
		propertyDTO = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		searchParamDTO = mock(PropertySearchParametersDTO.class);
		given(sessionService.validate(TOKEN)).willReturn(OWNER);
		given(searchParamFactory.getSearchParametersDTO(searchParam)).willReturn(searchParamDTO);
	}

	@Test
	public void givenTokenAndPropertyInfoWhenUploadPropertyThenUsesSessionServiceToRetrieveOwner() throws Throwable {
		propertyResource.uploadProperty(TOKEN, propertyDTO);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenUploadPropertyThenReturnsUnauthorizedStatusCodeIfInvalidToken()
			throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenUploadPropertyThenReturnsForbiddenStatusCodeIfAccessIsForbidden()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(service).uploadProperty(OWNER, propertyDTO);
		Response response = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenUploadPropertyThenCallsServiceAntiCorruption() throws Throwable {
		propertyResource.uploadProperty(TOKEN, propertyDTO);
		verify(service).uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenTokenAndValidPropertyInformationsWhenUploadPropertyThenReturnsOkResponse() {
		Response result = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndInvalidInformationsWhenUploadPropertyThenResponseIsBadRequest() throws Throwable {
		doThrow(InvalidPropertyInformationException.class).when(service).uploadProperty(OWNER, propertyDTO);
		Response result = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenSearchPropertiesThenUsesSessionServiceToValidate() throws Throwable {
		propertyResource.searchProperties(TOKEN, searchParam);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenATokenWhenSearchPropertiesThenReturnsUnauthorizedStatusCodeIfInvalidToken() throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.searchProperties(TOKEN, searchParam);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenSearchPropertiesThenReturnsForbiddenStatusCodeIfForbiddenAccess() throws Throwable {
		doThrow(ForbiddenAccessException.class).when(service).getPropertiesSearchResult(OWNER, searchParamDTO);
		Response response = propertyResource.searchProperties(TOKEN, searchParam);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenQueryParamsWhenSearchPropertiesThenUsesFactoryToBuildSearchParamDTO() throws Exception {
		propertyResource.searchProperties(TOKEN, searchParam);
		verify(searchParamFactory).getSearchParametersDTO(searchParam);
	}

	@Test
	public void givenQueryParamsWhenSearchPropertiesThenReturnsBadRequestIfFactoryThrowsException() throws Exception {
		doThrow(InvalidSearchParameterException.class).when(searchParamFactory).getSearchParametersDTO(searchParam);
		Response response = propertyResource.searchProperties(TOKEN, searchParam);
		assertEquals(Status.BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void whenSearchPropertiesThenUsesTheServiceToGetProperties() throws Throwable {
		propertyResource.searchProperties(TOKEN, searchParam);
		verify(service).getPropertiesSearchResult(OWNER, searchParamDTO);
	}

	@Test
	public void whenSearchPropertiesThenConvertToJsonPropertyDTOs() throws Throwable {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getPropertiesSearchResult(OWNER, searchParamDTO)).willReturn(dtos);

		Response result = propertyResource.searchProperties(TOKEN, searchParam);

		assertEquals(dtos, result.getEntity());
	}

	@Test
	public void whenSearchPropertiesThenReturnsInvalidRequestIfSearchFiltersAreInvalid() throws Throwable {
		doThrow(InvalidSearchParameterException.class).when(service).getPropertiesSearchResult(OWNER, searchParamDTO);
		Response result = propertyResource.searchProperties(TOKEN, searchParam);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenValidateTokeWithSessionService() throws Throwable {
		propertyResource.editProperty(TOKEN, propertyDTO);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsUnauthorizedStatusCodeIfInvalidToken()
			throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsForbiddenStatusCodeIfAccessIsForbidden()
			throws Throwable {
		doThrow(ForbiddenAccessException.class).when(service).editPropertyFeatures(OWNER, propertyDTO);
		Response response = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenUsesPropertyServiceAntiCorruptionToEditProperty()
			throws Throwable {
		propertyResource.editProperty(TOKEN, propertyDTO);
		verify(service).editPropertyFeatures(OWNER, propertyDTO);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsBadRequestIfServiceACThrowsException()
			throws Throwable {
		doThrow(InvalidPropertyInformationException.class).when(service).editPropertyFeatures(OWNER, propertyDTO);
		Response result = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsStatusOkIfNoExceptionThrown() {
		Response result = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetPropertiesFromUserThenUsesSessionsServiceToRetrieveOwner() throws Throwable {
		propertyResource.getPropertiesFromOwner(TOKEN);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenATokenWhenGetPropertiesFromUserThenReturnsUnauthorizedStatusCodeIfInvalidToken() throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenCallsService() throws Throwable {
		propertyResource.getPropertiesFromOwner(TOKEN);
		verify(service).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenReturnsStatusOKIfNoExceptionThrown() {
		Response result = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenReturnsForbiddenStatusIfAccessIsForbidden()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(service).getPropertiesFromOwner(OWNER);
		Response result = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenConvertPropertiesToJson() throws Throwable {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getPropertiesFromOwner(OWNER)).willReturn(dtos);

		Response result = propertyResource.getPropertiesFromOwner(TOKEN);

		assertEquals(dtos, result.getEntity());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenCheckIfTokenIsValid() throws Exception {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.UNAUTHORIZED, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenReturnsForbiddenStatusIfAccessIsForbidden()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(service).getPropertyAtAddress(OWNER, addressDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenReturnsNotFoundStatusIfNoPropertyIsFound()
			throws Exception {
		doThrow(PropertyNotFoundException.class).when(service).getPropertyAtAddress(OWNER, addressDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.NOT_FOUND, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenReturnsPropertyDTOAndOkStatus() throws Exception {
		given(service.getPropertyAtAddress(OWNER, addressDTO)).willReturn(propertyDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(propertyDTO, result.getEntity());
		assertEquals(Status.OK, result.getStatusInfo());
	}

	private ArrayList<PropertyDTO> createPropertyDTOsList() {
		ArrayList<PropertyDTO> dtos = new ArrayList<PropertyDTO>();
		dtos.add(propertyDTO);
		return dtos;
	}
}
