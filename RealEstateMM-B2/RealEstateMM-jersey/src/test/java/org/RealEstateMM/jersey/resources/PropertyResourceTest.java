package org.RealEstateMM.jersey.resources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceAntiCorruption;
import org.junit.Before;
import org.junit.Test;

public class PropertyResourceTest {

	private PropertyResource propertyResource;

	private PropertyServiceAntiCorruption serviceAC;
	private PropertyDTO propertyInfos;

	@Before
	public void setup() {
		serviceAC = mock(PropertyServiceAntiCorruption.class);
		propertyInfos = mock(PropertyDTO.class);
		propertyResource = new PropertyResource(serviceAC);
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
}
