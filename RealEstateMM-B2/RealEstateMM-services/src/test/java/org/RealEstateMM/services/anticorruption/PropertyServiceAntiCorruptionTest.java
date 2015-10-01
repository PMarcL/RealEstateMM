package org.RealEstateMM.services.anticorruption;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformations;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceAntiCorruptionTest {
	private final String ZIPCODE = "G6P7H7";

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyService service;
	private PropertyAddressValidator validator;
	private PropertyAddressInformations addressInfos;
	private PropertyInformations propertyInfos;

	@Before
	public void setup() {
		service = mock(PropertyService.class);
		validator = mock(PropertyAddressValidator.class);
		propertyInfos = mock(PropertyInformations.class);
		addressInfos = mock(PropertyAddressInformations.class);

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
