package org.RealEstateMM.services.property;

import static org.mockito.Mockito.*;

import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceAntiCorruptionTest {
	private final String ZIPCODE = "G6P7H7";

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyService service;
	private PropertyAddressValidator validator;
	private PropertyAddressDTO addressInfos;
	private PropertyDTO propertyInfos;

	@Before
	public void setup() {
		service = mock(PropertyService.class);
		validator = mock(PropertyAddressValidator.class);
		propertyInfos = mock(PropertyDTO.class);
		addressInfos = mock(PropertyAddressDTO.class);

		propertyAntiCorruption = new PropertyServiceAntiCorruption(service, validator);

		when(propertyInfos.getPropertyAddressInformations()).thenReturn(addressInfos);
		when(addressInfos.getZipCode()).thenReturn(ZIPCODE);
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(true);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksAddressValidity() {
		propertyAntiCorruption.upload(propertyInfos);
		verify(validator).zipCodeIsValid(ZIPCODE);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsTheService() {
		propertyAntiCorruption.upload(propertyInfos);
		verify(service).uploadProperty(propertyInfos);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyWithInvalidZipCodeThenThrowException() {
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(false);
		propertyAntiCorruption.upload(propertyInfos);
	}
}
