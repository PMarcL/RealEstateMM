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
import org.RealEstateMM.services.property.PropertyService;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private PropertyDTOAssembler assembler;
	private PropertyRepository repository;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyAddress address;
	private PropertyFeatures features;

	private PropertyService propertyService;

	@Before
	public void setup() {
		assembler = mock(PropertyDTOAssembler.class);
		repository = mock(PropertyRepository.class);
		propertyService = new PropertyService(repository, assembler);

		propertyDTO = mock(PropertyDTO.class);
		property = mock(Property.class);
		address = mock(PropertyAddress.class);
		features = mock(PropertyFeatures.class);
		given(repository.getPropertyAtAddress(address)).willReturn(Optional.of(property));
		assemblerHappyPath();
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
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyAddress() {
		propertyService.editPropertyFeatures(propertyDTO);
		verify(assembler).getPropertyAddressFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(propertyDTO);
		verify(assembler).getFeaturesFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenGetsPropertyWithAddress() {
		given(assembler.getPropertyAddressFromDTO(propertyDTO)).willReturn(address);
		propertyService.editPropertyFeatures(propertyDTO);
		verify(repository).getPropertyAtAddress(address);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUpdatesPropertyWithNewFeatures() {
		propertyService.editPropertyFeatures(propertyDTO);
		verify(property).updateFeatures(features);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUpdatesPropertyInRepository() {
		propertyService.editPropertyFeatures(propertyDTO);
		verify(repository).updateProperty(property);
	}

	private void assemblerHappyPath() {
		given(assembler.getFeaturesFromDTO(propertyDTO)).willReturn(features);
		given(assembler.getPropertyAddressFromDTO(propertyDTO)).willReturn(address);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
