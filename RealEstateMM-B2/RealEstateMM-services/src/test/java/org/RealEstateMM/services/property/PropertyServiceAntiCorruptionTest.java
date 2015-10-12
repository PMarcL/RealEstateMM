package org.RealEstateMM.services.property;

import static org.mockito.Mockito.*;
import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceAntiCorruptionTest {
	private final String ZIPCODE = "G6P7H7";
	private final String TYPE = "house";
	private final String STATUS = "on sale";
	private final String OWNER = "owner90";

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyServiceHandler service;
	private PropertyInformationsValidator validator;
	private PropertyAddressDTO addressInfos;
	private PropertyDTO propertyDTO;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		validator = mock(PropertyInformationsValidator.class);
		propertyDTO = mock(PropertyDTO.class);
		addressInfos = mock(PropertyAddressDTO.class);

		propertyAntiCorruption = new PropertyServiceAntiCorruption(service, validator);

		propertyDTOReturnsValidInfos();
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksAddressValidity() {
		propertyAntiCorruption.uploadProperty(propertyDTO);
		verify(validator).zipCodeIsValid(ZIPCODE);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenCallsTheService() {
		propertyAntiCorruption.uploadProperty(propertyDTO);
		verify(service).uploadProperty(propertyDTO);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyWithInvalidZipCodeThenThrowException() {
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyTypeValidity() {
		propertyAntiCorruption.uploadProperty(propertyDTO);
		verify(validator).propertyTypeIsValid(TYPE);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyTypeNotValidThrowException() {
		when(validator.propertyTypeIsValid(TYPE)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyStatusValidity() {
		propertyAntiCorruption.uploadProperty(propertyDTO);
		verify(validator).propertyStatusIsValid(STATUS);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyStatusNotValidThrowException() {
		when(validator.propertyStatusIsValid(STATUS)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(propertyDTO);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenCallsServiceToEditProperty() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(service).editPropertyFeatures(propertyDTO);
	}

	@Test
	public void whenGetAllPropertiesThenCallsPropertyService() {
		propertyAntiCorruption.getAllProperties();
		verify(service).getAllProperties();
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenCallsService() {
		propertyAntiCorruption.getPropertiesFromOwner(OWNER);
		verify(service).getPropertiesFromOwner(OWNER);
	}

	private void propertyDTOReturnsValidInfos() {
		when(propertyDTO.getPropertyAddress()).thenReturn(addressInfos);
		when(propertyDTO.getPropertyType()).thenReturn(TYPE);
		when(propertyDTO.getPropertyStatus()).thenReturn(STATUS);
		when(addressInfos.getZipCode()).thenReturn(ZIPCODE);
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(true);
		when(validator.propertyTypeIsValid(TYPE)).thenReturn(true);
		when(validator.propertyStatusIsValid(STATUS)).thenReturn(true);
	}

}
