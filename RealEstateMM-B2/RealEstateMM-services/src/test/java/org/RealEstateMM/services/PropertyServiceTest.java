package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.RealEstateMM.services.property.InvalidSearchParameterException;
import org.RealEstateMM.services.property.PropertySearchParametersParser;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private final String ORDER_BY = "recently_uploaded_last";
	private final PropertySearchParameters SEARCH_PARAM = PropertySearchParameters.RECENTLY_UPLOADED_LAST;
	private final String OWNER = "owner90";
	private final String PSEUDO = "pseudo32";

	private PropertyDTOAssembler assembler;
	private PropertyDTO propertyDTO;
	private PropertyAddressDTO addressDTO;
	private PropertySearchParametersDTO searchParamsDTO;
	private Property property;
	private PropertyAddress address;
	private PropertyFeatures features;
	private PropertySearchParametersParser searchParameterParser;
	private Properties properties;

	private PropertyService propertyService;

	@Before
	public void setup() throws Throwable {
		assembler = mock(PropertyDTOAssembler.class);
		properties = mock(Properties.class);
		searchParameterParser = mock(PropertySearchParametersParser.class);
		propertyService = new PropertyService(assembler, properties, searchParameterParser);

		given(searchParameterParser.getParsedSearchParameter(ORDER_BY)).willReturn(SEARCH_PARAM);
		propertyDTO = mock(PropertyDTO.class);
		addressDTO = mock(PropertyAddressDTO.class);
		searchParamsDTO = mock(PropertySearchParametersDTO.class);
		property = mock(Property.class);
		features = mock(PropertyFeatures.class);
		address = mock(PropertyAddress.class);
		configureAssembler();
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUseAssemblerToBuildProperty() {
		propertyService.uploadProperty(OWNER, propertyDTO);
		verify(assembler).fromDTO(propertyDTO);
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenAddPropertyToProperties() {
		propertyService.uploadProperty(OWNER, propertyDTO);
		verify(properties).addProperty(property);
	}

	@Test
	public void whenGetAllPropertiesThenGetsAllPropertyFromProperties() {
		propertyService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		verify(properties).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesThenBuildDTOsFromPropertiesWithAssembler() {
		given(properties.getAllProperties()).willReturn(buildPropertiesList());
		propertyService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetAllPropertiesThenReturnsDTOsOfAllProperties() {
		given(properties.getAllProperties()).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = propertyService.getPropertiesSearchResult(PSEUDO, searchParamsDTO);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(assembler).getFeaturesFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUsesPropertiesToUpdateProperty() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(properties).editPropertyFeatures(property, features);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenGetsPropertyWithProperties() {
		propertyService.getPropertiesFromOwner(OWNER);
		verify(properties).getPropertiesFromOwner(OWNER);
	}

	@Test
	public void givenPropertyOwnerWhenGetPropertiesFromOwnerThenConvertPropertiesWithAssembler() {
		given(properties.getPropertiesFromOwner(OWNER)).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = propertyService.getPropertiesFromOwner(OWNER);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerToGetAddress() throws Exception {
		propertyService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(assembler).getAddressFromDTO(addressDTO);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesPropertiesWithAddress() throws Exception {
		propertyService.getPropertyAtAddress(PSEUDO, addressDTO);
		verify(properties).getPropertyAtAddress(address);
	}

	@Test
	public void givenAnAddressWhenGetPropertyAtAddressThenUsesAssemblerAndReturnsPropertyDTO() throws Exception {
		given(properties.getPropertyAtAddress(address)).willReturn(property);

		PropertyDTO result = propertyService.getPropertyAtAddress(PSEUDO, addressDTO);

		verify(assembler).toDTO(property);
		assertEquals(propertyDTO, result);
	}

	private void configureAssembler() {
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(assembler.fromDTO(propertyDTO)).willReturn(property);
		given(assembler.getFeaturesFromDTO(propertyDTO)).willReturn(features);
		given(assembler.getAddressFromDTO(addressDTO)).willReturn(address);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
