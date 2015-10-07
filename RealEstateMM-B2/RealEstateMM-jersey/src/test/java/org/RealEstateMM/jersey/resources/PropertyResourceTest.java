package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.anticorruption.InvalidPropertyInformationException;
import org.RealEstateMM.services.anticorruption.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

public class PropertyResourceTest {

	private PropertyResource propertyResource;
	private PropertyServiceAntiCorruption serviceAC;
	private PropertyDTO propertyInfos;
	private PropertyFeaturesDTO features;

	private PropertyService service;

	@Before
	public void setup() {
		serviceAC = mock(PropertyServiceAntiCorruption.class);
		service = mock(PropertyService.class);
		propertyResource = new PropertyResource(serviceAC, service);

		propertyInfos = mock(PropertyDTO.class);
		features = mock(PropertyFeaturesDTO.class);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsServiceAntiCorruption() {
		propertyResource.uploadProperty(propertyInfos);
		verify(serviceAC).upload(propertyInfos);
	}

	@Test
	public void givenValidPropertyInformationsWhenUploadPropertyThenReturnsOkResponse() {
		Response result = propertyResource.uploadProperty(propertyInfos);
		assertEquals(Status.OK, result.getStatusInfo());
	}

	@Test
	public void givenInvalidInformationsWhenUploadPropertyThenResponseIsBadRequest() {
		doThrow(InvalidPropertyInformationException.class).when(serviceAC).upload(propertyInfos);
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
		verify(serviceAC).editProperty(features);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenReturnsBadRequestIfServiceACThrowsException() {
		doThrow(InvalidPropertyInformationException.class).when(serviceAC).editProperty(features);
		Response result = propertyResource.editProperty(features);
		assertEquals(Status.BAD_REQUEST, result.getStatusInfo());
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenReturnsStatusOkIfNoExceptionThrown() {
		Response result = propertyResource.editProperty(features);
		assertEquals(Status.OK, result.getStatusInfo());
	}
}
