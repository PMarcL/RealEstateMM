package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTOAssembler;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTOAssembler;
import org.RealEstateMM.services.property.PropertyService;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private PropertyDTOAssembler assembler;
	private PropertyRepository repository;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyAddress address;
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
		address = mock(PropertyAddress.class);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		configureFeaturesAssembler();
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
	public void givenPropertyFeaturesWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(featuresDTO);
		verify(featuresAssembler).fromDTO(featuresDTO);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenAssemblesPropertyAddress() {
		propertyService.editPropertyFeatures(featuresDTO);
		verify(featuresAssembler).getAddressFromDTO(featuresDTO);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenGetsPropertyWithZipCode() {
		propertyService.editPropertyFeatures(featuresDTO);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenUpdatesPropertyWithNewFeatures() {
		propertyService.editPropertyFeatures(featuresDTO);
		verify(property).updateFeatures(features);
	}

	@Test
	public void givenPropertyFeaturesWhenEditPropertyThenUpdatesPropertyInRepository() {
		propertyService.editPropertyFeatures(featuresDTO);
		verify(repository).updateProperty(property);
	}

	private void configureFeaturesAssembler() {
		given(featuresAssembler.fromDTO(featuresDTO)).willReturn(features);
		given(featuresAssembler.getAddressFromDTO(featuresDTO)).willReturn(address);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
