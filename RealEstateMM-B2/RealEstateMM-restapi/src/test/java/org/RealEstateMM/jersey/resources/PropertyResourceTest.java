package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.authentication.session.InvalidSessionTokenException;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.RealEstateMM.services.search.SearchServiceHandler;

public class PropertyResourceTest {

	private final String NO_QUERY_PARAM = null;
	private final String QUERY_PARAM = "recently_uploaded_last";
	private final String OWNER = "owner90";
	private final String TOKEN = "token";

	private PropertyResource propertyResource;
	private PropertyDTO propertyDTO;
	private PropertyAddressDTO addressDTO;
	private PropertyServiceHandler propertyService;
	private SessionService sessionService;
	private SearchServiceHandler searchService;

	@Before
	public void setup() throws Throwable {
		propertyService = mock(PropertyServiceHandler.class);
		sessionService = mock(SessionService.class);
		searchService = mock(SearchServiceHandler.class);
		propertyResource = new PropertyResource(propertyService, sessionService, searchService);

		propertyDTO = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		given(sessionService.validate(TOKEN)).willReturn(OWNER);
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
		doThrow(ForbiddenAccessException.class).when(propertyService).uploadProperty(OWNER, propertyDTO);
		Response response = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenUploadPropertyThenCallsServiceAntiCorruption() throws Throwable {
		propertyResource.uploadProperty(TOKEN, propertyDTO);
		verify(propertyService).uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenTokenAndValidPropertyInformationsWhenUploadPropertyThenReturnsOkResponse() {
		Response result = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndInvalidInformationsWhenUploadPropertyThenResponseIsBadRequest() throws Throwable {
		doThrow(InvalidPropertyInformationException.class).when(propertyService).uploadProperty(OWNER, propertyDTO);
		Response result = propertyResource.uploadProperty(TOKEN, propertyDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetAllPropertiesThenUsesSessionServiceToValidate() throws Throwable {
		propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		verify(sessionService).validate(TOKEN);
	}

	@Test
	public void givenATokenWhenGetAllPropertiesThenReturnsUnauthorizedStatusCodeIfInvalidToken() throws Throwable {
		doThrow(InvalidSessionTokenException.class).when(sessionService).validate(TOKEN);
		Response response = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.UNAUTHORIZED, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetAllPropertertiesThenReturnsForbiddenStatusCodeIfForbiddenAccess() throws Throwable {
		doThrow(ForbiddenAccessException.class).when(searchService).getAllProperties(OWNER);
		Response response = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenATokenWhenGetOrderedPropertertiesThenReturnsForbiddenStatusCodeIfForbiddenAccess()
			throws Throwable {
		doThrow(ForbiddenAccessException.class).when(searchService).getOrderedProperties(OWNER, QUERY_PARAM);
		Response response = propertyResource.getProperties(TOKEN, QUERY_PARAM);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenUsesTheServiceToGetProperties() throws Throwable {
		propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		verify(searchService).getAllProperties(OWNER);
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenAlwaysReturnsStatusOK() {
		Response result = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenConvertToJsonPropertyDTOs() throws Throwable {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(searchService.getAllProperties(OWNER)).willReturn(dtos);

		Response result = propertyResource.getProperties(TOKEN, NO_QUERY_PARAM);

		assertEquals(dtos, result.getEntity());
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenUsesTheServiceToGetOrderedProperties() throws Throwable {
		propertyResource.getProperties(TOKEN, QUERY_PARAM);
		verify(searchService).getOrderedProperties(OWNER, QUERY_PARAM);
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenReturnsInvalidRequestIfSearchFilterIsInvalid() throws Throwable {
		doThrow(InvalidSearchParameterException.class).when(searchService).getOrderedProperties(OWNER, QUERY_PARAM);
		Response result = propertyResource.getProperties(TOKEN, QUERY_PARAM);
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
		doThrow(ForbiddenAccessException.class).when(propertyService).editPropertyFeatures(OWNER, propertyDTO);
		Response response = propertyResource.editProperty(TOKEN, propertyDTO);
		assertEquals(Status.FORBIDDEN, response.getStatusInfo());
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenUsesPropertyServiceAntiCorruptionToEditProperty()
			throws Throwable {
		propertyResource.editProperty(TOKEN, propertyDTO);
		verify(propertyService).editPropertyFeatures(OWNER, propertyDTO);
	}

	@Test
	public void givenTokenAndPropertyDTOWhenEditPropertyThenReturnsBadRequestIfServiceACThrowsException()
			throws Throwable {
		doThrow(InvalidPropertyInformationException.class).when(propertyService).editPropertyFeatures(OWNER,
				propertyDTO);
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
		verify(searchService).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenReturnsStatusOKIfNoExceptionThrown() {
		Response result = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenReturnsForbiddenStatusIfAccessIsForbidden()
			throws Exception {
		doThrow(ForbiddenAccessException.class).when(searchService).getPropertiesFromOwner(OWNER);
		Response result = propertyResource.getPropertiesFromOwner(TOKEN);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnersTokenWhenGetPropertiesFromOwnerThenConvertPropertiesToJson() throws Throwable {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(searchService.getPropertiesFromOwner(OWNER)).willReturn(dtos);

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
		doThrow(ForbiddenAccessException.class).when(searchService).getPropertyAtAddress(OWNER, addressDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.FORBIDDEN, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenReturnsNotFoundStatusIfNoPropertyIsFound()
			throws Exception {
		doThrow(PropertyNotFoundException.class).when(searchService).getPropertyAtAddress(OWNER, addressDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.NOT_FOUND, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndInvalidPropertyInformationsWhenGetPropertyAtAddrssThenReturnBadRequest()
			throws Exception, PropertyNotFoundException, InvalidPropertyInformationException {
		doThrow(InvalidPropertyInformationException.class).when(searchService).getPropertyAtAddress(OWNER, addressDTO);
		Response result = propertyResource.getPropertyAtAddress(TOKEN, addressDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenTokenAndAddressDTOWhenGetPropertyAtAddressThenReturnsPropertyDTOAndOkStatus() throws Exception {
		given(searchService.getPropertyAtAddress(OWNER, addressDTO)).willReturn(propertyDTO);
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
