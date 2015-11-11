package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.search.InvalidFilterException;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public class PropertyResourceTest {

	private final PropertySearchFilter NO_QUERY_PARAM = null;
	private final String OWNER = "owner90";

	private PropertyResource propertyResource;
	private PropertyDTO propertyDTO;
	private PropertySearchFilter searchFilter;
	private PropertyServiceHandler service;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		propertyResource = new PropertyResource(service);

		propertyDTO = mock(PropertyDTO.class);
		searchFilter = mock(PropertySearchFilter.class);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsServiceAntiCorruption() {
		propertyResource.uploadProperty(propertyDTO);
		verify(service).uploadProperty(propertyDTO);
	}

	@Test
	public void givenValidPropertyInformationsWhenUploadPropertyThenReturnsOkResponse() {
		Response result = propertyResource.uploadProperty(propertyDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenInvalidInformationsWhenUploadPropertyThenResponseIsBadRequest() {
		doThrow(InvalidPropertyInformationException.class).when(service).uploadProperty(propertyDTO);
		Response result = propertyResource.uploadProperty(propertyDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenUsesTheServiceToGetProperties() {
		propertyResource.getProperties(NO_QUERY_PARAM);
		verify(service).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenAlwaysReturnsStatusOK() {
		Response result = propertyResource.getProperties(NO_QUERY_PARAM);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void whenGetAllPropertiesWithNoQueryParamThenConvertToJsonPropertyDTOs() {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getAllProperties()).willReturn(dtos);

		Response result = propertyResource.getProperties(NO_QUERY_PARAM);

		assertEquals(dtos, result.getEntity());
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenUsesTheServiceToGetOrderedProperties() {
		propertyResource.getProperties(searchFilter);
		verify(service).getOrderedProperties(searchFilter);
	}

	@Test
	public void whenGetAllPropertiesWithQueryParamThenReturnsInvalidRequestIfSearchFilterIsInvalid() {
		doThrow(InvalidFilterException.class).when(service).getOrderedProperties(searchFilter);
		Response result = propertyResource.getProperties(searchFilter);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUsesPropertyServiceAntiCorruptionToEditProperty() {
		propertyResource.editProperty(propertyDTO);
		verify(service).editPropertyFeatures(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenReturnsBadRequestIfServiceACThrowsException() {
		doThrow(InvalidPropertyInformationException.class).when(service).editPropertyFeatures(propertyDTO);
		Response result = propertyResource.editProperty(propertyDTO);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenReturnsStatusOkIfNoExceptionThrown() {
		Response result = propertyResource.editProperty(propertyDTO);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromUserThenCallsService() {
		propertyResource.getPropertiesFromOwner(OWNER);
		verify(service).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromUserThenReturnsStatusOKIfNoExceptionThrown() {
		Response result = propertyResource.getPropertiesFromOwner(OWNER);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenAnOwnerWhenGetPropertiesFromUserThenConvertPropertiesToJson() {
		ArrayList<PropertyDTO> dtos = createPropertyDTOsList();
		given(service.getPropertiesFromOwner(OWNER)).willReturn(dtos);

		Response result = propertyResource.getPropertiesFromOwner(OWNER);

		assertEquals(dtos, result.getEntity());
	}

	private ArrayList<PropertyDTO> createPropertyDTOsList() {
		ArrayList<PropertyDTO> dtos = new ArrayList<PropertyDTO>();
		dtos.add(propertyDTO);
		return dtos;
	}
}
