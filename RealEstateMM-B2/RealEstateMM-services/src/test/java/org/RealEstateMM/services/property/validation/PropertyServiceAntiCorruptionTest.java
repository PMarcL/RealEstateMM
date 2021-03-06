package org.RealEstateMM.services.property.validation;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyFeaturesDTO;
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
	private final double VALID_PRICE = 200000.0;
	private final int A_VALID_YEAR_OF_CONSTRUCTION = 1999;

	private PropertyServiceAntiCorruption propertyAntiCorruption;
	private PropertyServiceHandler service;
	private PropertyInformationsValidator validator;
	private PropertyAddressDTO addressDTO;
	private PropertyDTO propertyDTO;
	private PropertyFeaturesDTO featuresDTO;

	@Before
	public void setup() {
		service = mock(PropertyServiceHandler.class);
		validator = mock(PropertyInformationsValidator.class);
		propertyDTO = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		featuresDTO = mock(PropertyFeaturesDTO.class);

		propertyAntiCorruption = new PropertyServiceAntiCorruption(service, validator);

		given(propertyDTO.getPropertyFeatures()).willReturn(featuresDTO);
		featuresDTOReturnsValidInfos();
		propertyDTOReturnsValidInfos();
	}

	@Test
	public void givenPropertyDTOWhenUploadPropertyThenChecksAddressValidity() throws Throwable {
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
		verify(validator).zipCodeIsValid(ZIPCODE);
	}

	@Test
	public void givenPropertyDTOWhenUploadPropertyThenCallsTheService() throws Throwable {
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
		verify(service).uploadProperty(OWNER, propertyDTO);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyDTOWhenUploadPropertyWithInvalidZipCodeThenThrowException() throws Throwable {
		when(validator.zipCodeIsValid(ZIPCODE)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenAPropertyDTOWhenUploadPropertyThenChecksPriceValidity() throws Throwable {
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
		verify(validator).priceIsValid(VALID_PRICE);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAPropertyDTOWhenUploadPropertyThenIfPriceIsInvalidThrowsException() throws Throwable {
		given(validator.priceIsValid(VALID_PRICE)).willReturn(false);
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyTypeValidity() throws Throwable {
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
		verify(validator).propertyTypeIsValid(TYPE);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyTypeNotValidThrowException() throws Throwable {
		when(validator.propertyTypeIsValid(TYPE)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenPropertyInformationsWhenUploadPropertyThenChecksPropertyStatusValidity() throws Throwable {
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
		verify(validator).propertyStatusIsValid(STATUS);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenPropertyInformationsWhenUploadPropertyThenIfPropertyStatusNotValidThrowException()
			throws Throwable {
		when(validator.propertyStatusIsValid(STATUS)).thenReturn(false);
		propertyAntiCorruption.uploadProperty(OWNER, propertyDTO);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenCallsServiceToEditProperty() throws Throwable {
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
		verify(service).editPropertyFeatures(OWNER, propertyDTO);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksNumberOfBedRoomsValidity() throws Throwable {
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
		verify(validator, times(2)).numberOfRoomsIsValid(NUMBER_OF_BEDROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksNumberOfBathRoomsValidity() throws Throwable {
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
		verify(validator, times(2)).numberOfRoomsIsValid(NUMBER_OF_BATHROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenChecksTotalNumberOfRoomsValidity() throws Throwable {
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
		verify(validator, times(1)).totalNumberOfRoomsIsValid(NUMBER_OF_BATHROOMS, NUMBER_OF_BEDROOMS,
				VALID_TOTAL_NUMBER_OF_ROOMS);
	}

	@Test
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenCheckYearOfConstructionValidity() throws Throwable {
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
		verify(validator, times(1)).yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAPropertyDTOWhenEditPropertyFeaturesThenThrowExceptionIfNumberOfRoomsIsInvalid() throws Throwable {
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(false);
		propertyAntiCorruption.editPropertyFeatures(OWNER, propertyDTO);
	}

	private void propertyDTOReturnsValidInfos() {
		given(propertyDTO.getPropertyAddress()).willReturn(addressDTO);
		given(propertyDTO.getPropertyType()).willReturn(TYPE);
		given(propertyDTO.getPropertyStatus()).willReturn(STATUS);
		given(propertyDTO.getPropertyPrice()).willReturn(VALID_PRICE);
		given(addressDTO.getZipCode()).willReturn(ZIPCODE);
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(true);
		given(validator.propertyTypeIsValid(TYPE)).willReturn(true);
		given(validator.propertyStatusIsValid(STATUS)).willReturn(true);
		given(validator.priceIsValid(VALID_PRICE)).willReturn(true);
		given(validator.yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION)).willReturn(true);
	}

	private void featuresDTOReturnsValidInfos() {
		given(featuresDTO.getNumberOfBedrooms()).willReturn(NUMBER_OF_BEDROOMS);
		given(featuresDTO.getNumberOfBathrooms()).willReturn(NUMBER_OF_BATHROOMS);
		given(featuresDTO.getTotalNumberOfRooms()).willReturn(VALID_TOTAL_NUMBER_OF_ROOMS);
		given(featuresDTO.getYearOfConstruction()).willReturn(A_VALID_YEAR_OF_CONSTRUCTION);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BATHROOMS)).willReturn(true);
		given(validator.numberOfRoomsIsValid(NUMBER_OF_BEDROOMS)).willReturn(true);
		given(validator.totalNumberOfRoomsIsValid(NUMBER_OF_BATHROOMS, NUMBER_OF_BEDROOMS, VALID_TOTAL_NUMBER_OF_ROOMS))
				.willReturn(true);
		given(validator.yearOfConstructionIsValid(A_VALID_YEAR_OF_CONSTRUCTION)).willReturn(true);
	}

}
