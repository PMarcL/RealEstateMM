package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.TokenInvalidException;
import org.RealEstateMM.domain.property.search.InvalidSearchParameterException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.user.ForbiddenAccessException;

public class PropertyResourceTest {

	private final String NO_QUERY_PARAM = null;
	private final String QUERY_PARAM = "recently_uploaded_last";
	private final String OWNER = "owner90";
	private final String TOKEN = "token";

	private PropertyResource propertyResource;
	private PropertyDTO propertyDTO;
	private PropertyServiceHandler service;
	private SessionService sessionService;

	@Before
	public void setup() throws TokenInvalidException {
		service = mock(PropertyServiceHandler.class);
		sessionService = mock(SessionService.class);
		propertyResource = new PropertyResource(service, sessionService);

		propertyDTO = mock(PropertyDTO.class);
		given(sessionService.validate(TOKEN)).willReturn(OWNER);
	}

	@Test
	public void givenTokenAndPropertyInfoWhenUploadPropertyThenUsesSessionServiceToRetrieveOwner() throws Exception {
		propertyResource.uploadProperty(TOKEN, propertyDTO);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenUploadPropertyThenReturnsUnauthorizedStatusCodeIfInvalidToken()
			throws Exception {
		doThrow(TokenInvalidException.class).when(sessionService).validate(TOKEN);
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
	public void givenATokenWhenGetAllPropertiesThenUsesSessionServiceToValidate() throws Exception {
		propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenATokenWhenGetAllPropertiesThenReturnsUnauthorizedStatusCodeIfInvalidToken() throws Exception {
		doThrow(TokenInvalidException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetAllPropertertiesThenReturnsForbiddenStatusCodeIfForbiddenAccess() throws Exception {
		doThrow(ForbiddenAccessException.class).when(service).getAllProperties(OWNER);
		Response response = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetOrderedPropertertiesThenReturnsForbiddenStatusCodeIfForbiddenAccess()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(service).getOrderedProperties(OWNER, QUERY_PARAM);
		Response response = propertyResource.getProperties(TOKEN, QUERY_PARAM);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenUsesTheServiceToGetProperties() throws Exception {
		propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		verify(service).getAllProperties(OWNER);
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenAlwaysReturnsStatusOK() {
		Response result = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenConvertToJsonPropertyDTOs() throws Exception {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getAllProperties(OWNER)).willReturn(dtos);

		Response result = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);

		assertEquals(dtos, result.getEntity());
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenUsesTheServiceToGetOrderedProperties() throws Exception {
		propertyResource.getProperties(TOKEN, QUERY_PARAM);
		verify(service).getOrderedProperties(OWNER, QUERY_PARAM);
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenReturnsInvalidRequestIfSearchFilterIsInvalid() throws Exception {
		doThrow(InvalidSearchParameterException.class).when(service).getOrderedProperties(OWNER, QUERY_PARAM);
		Response result = propertyResource.getProperties(TOKEN, QUERY_PARAM);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenValidateTokeWithSessionService() throws Exception {
		propertyResource.editProperty(TOKEN, propertyDTO);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsUnauthorizedStatusCodeIfInvalidToken()
			throws Exception {
		doThrow(TokenInvalidException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsForbiddenStatusCodeIfAccessIsForbidden()
			throws Exception {
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
	public void givenATokenWhenGetPropertiesFromUserThenUsesSessionsServiceToRetrieveOwner() throws Exception {
		propertyResource.getPropertiesFromOwner(TOKEN);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenATokenWhenGetPropertiesFromUserThenReturnsUnauthorizedStatusCodeIfInvalidToken() throws Exception {
		doThrow(TokenInvalidException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromUserThenCallsService() {
		propertyResource.getPropertiesFromOwner(TOKEN);
		verify(service).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromUserThenReturnsStatusOKIfNoExceptionThrown() {
		Response result = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromUserThenConvertPropertiesToJson() {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getPropertiesFromOwner(OWNER)).willReturn(dtos);

		Response result = propertyResource.getPropertiesFromOwner(TOKEN);

		assertEquals(dtos, result.getEntity());
	}

	private ArrayList<PropertyDTO> createPropertyDTOsList() {
		ArrayList<PropertyDTO> dtos = new ArrayList<PropertyDTO>();
		dtos.add(propertyDTO);
		return dtos;
	}
}
