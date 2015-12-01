package org.RealEstateMM.services.search.validation;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.junit.Before;
import org.junit.Test;

public class SearchServiceAntiCorruptionTest {
	private final String PSEUDO = "pseudo32";
	private final String OWNER = "owner90";
	private final String ZIPCODE = "G6P7H7";

	private PropertyAddressDTO addressDTO;
	private PropertyInformationsValidator validator;
	private SearchServiceHandler serviceHandler;
	private PropertySearchParametersDTO searchParams;

	private SearchServiceAntiCorruption service;

	@Before
	public void setup() {
		addressDTO = mock(PropertyAddressDTO.class);
		serviceHandler = mock(SearchServiceHandler.class);
		validator = mock(PropertyInformationsValidator.class);
		searchParams = mock(PropertySearchParametersDTO.class);

		service = new SearchServiceAntiCorruption(serviceHandler, validator);
		propertyDTOReturnsValidInfos();
	}

	@Test
	public void givenAddressDTOAndOwnerWhenGetPropertyAtAddressThenUsesService() throws Exception {
		service.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(serviceHandler).getPropertyAtAddress(PSEUDO, addressDTO);
	}

	@Test(expected = InvalidPropertyInformationException.class)
	public void givenAddressDTOWhenGetPropertyAtAddressWithInvalidZipCodeThenThrowsException() throws Throwable {
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(false);
		service.getPropertyAtAddress(PSEUDO, addressDTO);
	}

	@Test
	public void whenGetAllPropertiesThenCallsPropertyService() throws Throwable {
		service.getPropertiesSearchResult(PSEUDO, searchParams);
		verify(serviceHandler).getPropertiesSearchResult(PSEUDO, searchParams);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenCallsService() throws Throwable {
		service.getPropertiesFromOwner(OWNER);
		verify(serviceHandler).getPropertiesFromOwner(OWNER);
	}

	private void propertyDTOReturnsValidInfos() {
		given(addressDTO.getZipCode()).willReturn(ZIPCODE);
		given(validator.zipCodeIsValid(ZIPCODE)).willReturn(true);
	}
}
