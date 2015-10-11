package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyResourceTest {

	private PropertyResource propertyResource;
	private PropertyDTO propertyDTO;
	private PropertyServiceHandler service;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		propertyResource = new PropertyResource(service);

		propertyDTO = mock(PropertyDTO.class);
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
	public void whenGetAllPropertiesThenUsesTheServiceToGetProperties() {
		propertyResource.getAllProperties();
		verify(service).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesThenAlwaysReturnsStatusOK() {
		Response result = propertyResource.getAllProperties();
		assertEquals(Status.OK, result.getStatusInfo());
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
}
