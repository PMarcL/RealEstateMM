package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyResourceTest {

	private PropertyResource propertyResource;
	private PropertyDTO propertyInfos;
	private PropertyFeaturesDTO features;
	private PropertyServiceHandler service;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		propertyResource = new PropertyResource(service);

		propertyInfos = mock(PropertyDTO.class);
		features = mock(PropertyFeaturesDTO.class);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsServiceAntiCorruption() {
		propertyResource.uploadProperty(propertyInfos);
		verify(service).uploadProperty(propertyInfos);
	}

	@Test
	public void givenValidPropertyInformationsWhenUploadPropertyThenReturnsOkResponse() {
		Response result = propertyResource.uploadProperty(propertyInfos);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenInvalidInformationsWhenUploadPropertyThenResponseIsBadRequest() {
		doThrow(InvalidPropertyInformationException.class).when(service).uploadProperty(propertyInfos);
		Response result = propertyResource.uploadProperty(propertyInfos);
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
	public void givenPropertyFeaturesWhenEditPropertyThenUsesPropertyServiceAntiCorruptionToEditProperty() {
		propertyResource.editProperty(features);
		verify(service).editPropertyFeatures(features);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenReturnsBadRequestIfServiceACThrowsException() {
		doThrow(InvalidPropertyInformationException.class).when(service).editPropertyFeatures(features);
		Response result = propertyResource.editProperty(features);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenReturnsStatusOkIfNoExceptionThrown() {
		Response result = propertyResource.editProperty(features);
		assertEquals(Status.OK, result.getStatusInfo());
	}
}
