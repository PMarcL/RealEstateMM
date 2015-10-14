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
	private final int VALID_TOTAL_NUMBER_OF_ROOMS = 4;
	private final int A_VALID_YEAR_OF_CONSTRUCTION = 1999;

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
		given(featuresDTO.getTotalNumberOfRooms()).willReturn(VALID_TOTAL_NUMBER_OF_ROOMS);
		given(featuresDTO.getYearOfConstruction()).willReturn(A_VALID_YEAR_OF_CONSTRUCTION);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(true);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BEDROOMS)).willReturn(true);
		given(validator.totalNumberOfRoomsIsValid(NUMBER_OF_BATHROOMS, NUMBER_OF_BEDROOMS, VALID_TOTAL_NUMBER_OF_ROOMS)).willReturn(true);
		given(validator.yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION)).willReturn(true);
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
		verify(validator, times(2)).numberOfRoomsIsValid(NUMBER_OF_BEDROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksNumberOfBathRoomsValidity() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(2)).numberOfRoomsIsValid(NUMBER_OF_BATHROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksTotalNumberOfRoomsValidity() {
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(1)).totalNumberOfRoomsIsValid(NUMBER_OF_BATHROOMS, NUMBER_OF_BEDROOMS, VALID_TOTAL_NUMBER_OF_ROOMS);
	}
	
	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenCheckYearOfConstructionValidity(){
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
		verify(validator, times(1)).yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenThrowExceptionIfNumberOfRoomsIsInvalid() {
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(false);
		propertyAntiCorruption.editPropertyFeatures(propertyDTO);
	}

	private void propertyDTOReturnsValidInfos() {
		given(propertyDTO.getPropertyAddress()).willReturn(addressInfos);
		given(propertyDTO.getPropertyType()).willReturn(TYPE);
		given(propertyDTO.getPropertyStatus()).willReturn(STATUS);
		given(addressInfos.getZipCode()).willReturn(ZIPCODE);
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(true);
		given(validator.propertyTypeIsValid(TYPE)).willReturn(true);
		given(validator.propertyStatusIsValid(STATUS)).willReturn(true);
		given(validator.yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION)).willReturn(true);
	}

}
