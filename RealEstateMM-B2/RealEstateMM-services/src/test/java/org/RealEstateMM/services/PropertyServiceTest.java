package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTOAssembler;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTOAssembler;
import org.RealEstateMM.services.property.PropertyService;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private final String ZIPCODE = "G6P7H7";

	private PropertyDTOAssembler assembler;
	private PropertyRepository repository;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyFeaturesDTO featuresDTO;
	private PropertyFeaturesDTOAssembler featuresAssembler;
	private PropertyFeatures features;

	private PropertyService propertyService;

	@Before
	public void setup() {
		assembler = mock(PropertyDTOAssembler.class);
		repository = mock(PropertyRepository.class);
		featuresAssembler = mock(PropertyFeaturesDTOAssembler.class);
		propertyService = new PropertyService(repository, assembler, featuresAssembler);

		propertyDTO = mock(PropertyDTO.class);
		property = mock(Property.class);
		featuresDTO = mock(PropertyFeaturesDTO.class);
		features = mock(PropertyFeatures.class);
		given(repository.getPropertyWithZipCode(ZIPCODE)).willReturn(Optional.of(property));
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUseAssemblerToBuildProperty() {
		propertyService.uploadProperty(propertyDTO);
		verify(assembler).fromDTO(propertyDTO);
	}

	@Test
	public void givenAPropertyDTOWhenUploadsPropertyThenUseRepositoryToStoreNewProperty() {
		given(assembler.fromDTO(propertyDTO)).willReturn(property);
		propertyService.uploadProperty(propertyDTO);
		verify(repository).add(property);
	}

	@Test
	public void whenGetAllPropertiesThenGetsAllPropertyFromRepository() {
		propertyService.getAllProperties();
		verify(repository).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesThenBuildDTOsFromPropertiesWithAssembler() {
		given(repository.getAllProperties()).willReturn(buildPropertiesList());

		propertyService.getAllProperties();

		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetAllPropertiesThenReturnsDTOsOfAllProperties() {
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(repository.getAllProperties()).willReturn(buildPropertiesList());

		ArrayList<PropertyDTO> returnedDTOs = propertyService.getAllProperties();

		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	@Test
	public void givenPropertyFeaturesAndZipCodeWhenEditPropertyThenGetsPropertyWithZipCode() {
		propertyService.editPropertyFeatures(featuresDTO, ZIPCODE);
		verify(repository).getPropertyWithZipCode(ZIPCODE);
	}

	@Test
	public void givenPropertyFeaturesAndZipCodeWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(featuresDTO, ZIPCODE);
		verify(featuresAssembler).fromDTO(featuresDTO);
	}

	@Test
	public void givenPropertyFeaturesAndZipCodeWhenEditPropertyThenUpdatesPropertyWithNewFeatures() {
		given(featuresAssembler.fromDTO(featuresDTO)).willReturn(features);
		propertyService.editPropertyFeatures(featuresDTO, ZIPCODE);
		verify(property).updateFeatures(features);
	}

	@Test
	public void givenPropertyFeaturesAndZipCodeWhenEditPropertyThenUpdatesPropertyInRepository() {
		propertyService.editPropertyFeatures(featuresDTO, ZIPCODE);
		verify(repository).updateProperty(property);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
