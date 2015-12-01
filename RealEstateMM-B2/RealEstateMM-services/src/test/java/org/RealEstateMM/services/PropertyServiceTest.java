package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {
	private final String OWNER = "owner90";

	private PropertyDTOAssembler assembler;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyFeatures features;
	private Properties properties;

	private PropertyService propertyService;

	@Before
	public void setup() throws Throwable {
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
	public void givenPropertyDTOWhenEditPropertyThenAssemblesPropertyFeatures() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(assembler).getFeaturesFromDTO(propertyDTO);
	}

	@Test
	public void givenPropertyDTOWhenEditPropertyThenUsesPropertiesToUpdateProperty() {
		propertyService.editPropertyFeatures(OWNER, propertyDTO);
		verify(properties).editPropertyFeatures(property, features);
	}

	private void configureAssembler() {
		given(assembler.fromDTO(propertyDTO)).willReturn(property);
		given(assembler.getFeaturesFromDTO(propertyDTO)).willReturn(features);
	}
}
