package org.RealEstateMM.services.property;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.dtos.property.PropertyAddressDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceAntiCorruptionTest {
	private final String ZIPCODE = "G6P7H7";
	private final String TYPE = "house";
	private final String STATUS = "on sale";
	private final String OWNER = "owner90";
	private final int NUMBER_OF_BEDROOMS = 2;
	private final int NUMBER_OF_BATHROOMS = 2;
	private final int TOTAL_NUMBER_OF_ROOMS = 2;

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyServiceHandler service;
	private PropertyInformationsValidator validator;
	private PropertyAddressDTO addressInfos;
	private PropertyDTO propertyDTO;
	private PropertyFeaturesDTO featuresDTO;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		validator = mock(PropertyInformationsValidator.class);
		propertyDTO = mock(PropertyDTO.class);
		addressInfos = mock(PropertyAddressDTO.class);
		featuresDTO = mock(PropertyFeaturesDTO.class);

		propertyAntiCorruption = new PropertyServiceAntiCorruption(service, validator);

		given(propertyDTO.getPropertyFeatures()).willReturn(featuresDTO);
		featuresDTOReturnsValidInfos();
		propertyDTOReturnsValidInfos();
	}

	private void featuresDTOReturnsValidInfos() {
		given(featuresDTO.getNumberOfBedrooms()).willReturn(NUMBER_OF_BEDROOMS);
		given(featuresDTO.getNumberOfBathrooms()).willReturn(NUMBER_OF_BATHROOMS);
		given(featuresDTO.getTotalNumberOfRooms()).willReturn(TOTAL_NUMBER_OF_ROOMS);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(true);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BEDROOMS)).willReturn(true);
		given(validator.numberOfRoomsIsValid(TOTAL_NUMBER_OF_ROOMS)).willReturn(true);
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

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksNumberOfBedRoomsValidity() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(3)).numberOfRoomsIsValid(NUMBER_OF_BEDROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksNumberOfBathRoomsValidity() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(3)).numberOfRoomsIsValid(NUMBER_OF_BATHROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksTotalNumberOfRoomsValidity() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(3)).numberOfRoomsIsValid(TOTAL_NUMBER_OF_ROOMS);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenThrowExceptionIfNumberOfRoomsIsInvalid() {
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(false);
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
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
