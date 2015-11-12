package org.RealEstateMM.services;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private final String OWNER = "owner90";
	private final String PSEUDO = "pseudo32";

	private PropertyDTOAssembler assembler;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyFeatures features;
	private PropertySearchFilter filter;
	private Properties properties;

	private PropertyService propertyService;

	@Before
	public void setup() {
		assembler = mock(PropertyDTOAssembler.class);
		properties = mock(Properties.class);
		propertyService = new PropertyService(assembler, properties);

		propertyDTO = mock(PropertyDTO.class);
		property = mock(Property.class);
		features = mock(PropertyFeatures.class);
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
		propertyService.getAllProperties(PSEUDO);
		verify(properties).getAllProperties();
	}

	@Test
	public void whenGetAllPropertiesThenBuildDTOsFromPropertiesWithAssembler() {
		given(properties.getAllProperties()).willReturn(buildPropertiesList());
		propertyService.getAllProperties(PSEUDO);
		verify(assembler).toDTO(property);
	}

	@Test
	public void whenGetAllPropertiesThenReturnsDTOsOfAllProperties() {
		given(properties.getAllProperties()).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = propertyService.getAllProperties(PSEUDO);
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
	public void givenASearchFilterWhenGetOrderedPropertiesThenReturnsPropertiesOrderedByProperties() {
		given(properties.getOrderedProperties(filter)).willReturn(buildPropertiesList());
		List<PropertyDTO> returnedDTOs = propertyService.getOrderedProperties(PSEUDO, filter);
		assertTrue(returnedDTOs.contains(propertyDTO));
	}

	private void configureAssembler() {
		given(assembler.toDTO(property)).willReturn(propertyDTO);
		given(assembler.fromDTO(propertyDTO)).willReturn(property);
		given(assembler.getFeaturesFromDTO(propertyDTO)).willReturn(features);
	}

	private ArrayList<Property> buildPropertiesList() {
		ArrayList<Property> returnedProperties = new ArrayList<Property>();
		returnedProperties.add(property);
		return returnedProperties;
	}
}
