package org.RealEstateMM.services.anticorruption;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.PropertyService;
import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceAntiCorruptionTest {
	private final String ZIPCODE = "G6P7H7";
	private final String TYPE = "house";
	private final String STATUS = "on sale";

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyService service;
	private PropertyInformationsValidator validator;
	private PropertyAddressDTO addressInfos;
	private PropertyDTO propertyDTO;
	private PropertyFeaturesDTO features;

	@Before
	public void setup() {
		service = mock(PropertyService.class);
		validator = mock(PropertyInformationsValidator.class);
		propertyDTO = mock(PropertyDTO.class);
		addressInfos = mock(PropertyAddressDTO.class);

		propertyAntiCorruption = new PropertyServiceAntiCorruption(service, validator);

		propertyDTOReturnsValidInfos();
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksAddressValidity() {
		propertyAntiCorruption.upload(propertyDTO);
		verify(validator).zipCodeIsValid(ZIPCODE);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsTheService() {
		propertyAntiCorruption.upload(propertyDTO);
		verify(service).uploadProperty(propertyDTO);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyWithInvalidZipCodeThenThrowException() {
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(false);
		propertyAntiCorruption.upload(propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyTypeValidity() {
		propertyAntiCorruption.upload(propertyDTO);
		verify(validator).propertyTypeIsValid(TYPE);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyTypeNotValidThrowException() {
		when(validator.propertyTypeIsValid(TYPE)).thenReturn(false);
		propertyAntiCorruption.upload(propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyStatusValidity() {
		propertyAntiCorruption.upload(propertyDTO);
		verify(validator).propertyStatusIsValid(STATUS);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyStatusNotValidThrowException() {
		when(validator.propertyStatusIsValid(STATUS)).thenReturn(false);
		propertyAntiCorruption.upload(propertyDTO);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenCallsServiceToEditProperty() {
		propertyAntiCorruption.editProperty(features, ZIPCODE);
		verify(service).editPropertyFeatures(features, ZIPCODE);
	}

	private void propertyDTOReturnsValidInfos() {
		when(propertyDTO.getPropertyAddressDTO()).thenReturn(addressInfos);
		when(propertyDTO.getPropertyType()).thenReturn(TYPE);
		when(propertyDTO.getPropertyStatus()).thenReturn(STATUS);
		when(addressInfos.getZipCode()).thenReturn(ZIPCODE);
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(true);
		when(validator.propertyTypeIsValid(TYPE)).thenReturn(true);
		when(validator.propertyStatusIsValid(STATUS)).thenReturn(true);
	}

}
