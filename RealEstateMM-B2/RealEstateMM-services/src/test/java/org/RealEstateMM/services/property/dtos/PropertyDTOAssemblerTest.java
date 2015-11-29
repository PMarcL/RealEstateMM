package org.RealEstateMM.services.property.dtos;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.junit.Before;
import org.junit.Test;

public class PropertyDTOAssemblerTest {
	private final double DELTA = 0.001;
	private final double A_PRICE = 200000.00;
	private final PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ON_SALE;
	private final PropertyType A_PROPERTY_TYPE = PropertyType.HOUSE;
	private final String OWNER_PSEUDO = "John90";

	private PropertyAddress propertyAddress;
	private PropertyAddressDTO addressDTO;
	private PropertyDTO propertyDTO;
	private Property property;
	private PropertyAddressDTOAssembler addressAssembler;
	private PropertyAssembler assembler;
	private PropertyFeaturesDTO featuresDTO;
	private PropertyFeaturesDTOAssembler featuresAssembler;
	private PropertyFeatures features;

	@Before
	public void setup() {
		addressAssembler = mock(PropertyAddressDTOAssembler.class);
		featuresAssembler = mock(PropertyFeaturesDTOAssembler.class);

		assembler = new PropertyAssembler(addressAssembler, featuresAssembler);
		addressDTO = mock(PropertyAddressDTO.class);
		featuresDTO = mock(PropertyFeaturesDTO.class);
		features = mock(PropertyFeatures.class);
		configurePropertyMock();
		configurePropertyDTOMock();
	}

	@Test
	public void givenAPropertyInformationsWhenBuildToDTOThenReturnedDTOShouldHaveTheSameInformations() {
		given(addressAssembler.toDTO(propertyAddress)).willReturn(addressDTO);
		PropertyDTO propertyInfos = assembler.toDTO(property);

		assertEquals(PropertyType.getStringFromType(A_PROPERTY_TYPE), propertyInfos.getPropertyType());
		assertEquals(addressDTO, propertyInfos.getPropertyAddress());
		assertEquals(A_PRICE, propertyInfos.getPropertyPrice(), DELTA);
		assertEquals(OWNER_PSEUDO, propertyInfos.getPropertyOwner());
		assertEquals(PropertyStatus.getStringFromStatus(A_PROPERTY_STATUS), propertyInfos.getPropertyStatus());
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenBuildTheAddress() {
		assembler.fromDTO(propertyDTO);
		verify(addressAssembler).fromDTO(addressDTO);
	}

	@Test
	public void givenAPropertyDTOWhenBuildingFromDTOThenPropertyShouldHaveTheSameInformations() {
		PropertyDTO dto = assembler.toDTO(property);
		Property result = assembler.fromDTO(dto);

		assertEquals(A_PROPERTY_TYPE, result.getType());
		assertEquals(dto.getPropertyPrice(), result.getPrice(), DELTA);
		assertEquals(A_PROPERTY_STATUS, result.getStatus());
		assertEquals(propertyAddress, result.getAddress());
	}

	@Test
	public void whenGetFeaturesFromDTOThenCallsFeaturesAssembler() {
		assembler.getFeaturesFromDTO(propertyDTO);
		verify(featuresAssembler).fromDTO(featuresDTO);
	}

	@Test
	public void whenGetFeaturesFromDTOThenReturnsBuiltFeaturesByFeaturesAssembler() {
		given(featuresAssembler.fromDTO(featuresDTO)).willReturn(features);

		PropertyFeatures result = assembler.getFeaturesFromDTO(propertyDTO);

		assertEquals(features, result);
	}

	@Test
	public void whenGetAddressFromDTOThenCallsAddressAssembler() {
		assembler.getAddressFromDTO(addressDTO);
		verify(addressAssembler).fromDTO(addressDTO);
	}

	@Test
	public void whenGetAddressFromDTOThenReturnsBuiltAddressByAddressAssembler() {
		given(addressAssembler.fromDTO(addressDTO)).willReturn(propertyAddress);

		PropertyAddress result = assembler.getAddressFromDTO(addressDTO);

		assertEquals(propertyAddress, result);
	}

	@Test
	public void givenAPropertyWhenBuildingDTOThenBuildPropertyFeaturesDTO() {
		assembler.toDTO(property);
		verify(featuresAssembler).toDTO(features);
	}

	@Test
	public void givenAPropertyWhenBuildingDTOThenSetsPropertyFeaturesDTOWithCorrectDTO() {
		given(featuresAssembler.toDTO(features)).willReturn(featuresDTO);
		PropertyDTO dto = assembler.toDTO(property);
		assertEquals(featuresDTO, dto.getPropertyFeatures());
	}

	private void configurePropertyMock() {
		property = mock(Property.class);
		given(property.getAddress()).willReturn(propertyAddress);
		given(property.getFeatures()).willReturn(features);
		given(property.getOwner()).willReturn(OWNER_PSEUDO);
		given(property.getType()).willReturn(A_PROPERTY_TYPE);
		given(property.getPrice()).willReturn(A_PRICE);
		given(property.getStatus()).willReturn(A_PROPERTY_STATUS);
	}

	private void configurePropertyDTOMock() {
		propertyDTO = mock(PropertyDTO.class);
		given(propertyDTO.getPropertyAddress()).willReturn(addressDTO);
		given(propertyDTO.getPropertyOwner()).willReturn(OWNER_PSEUDO);
		given(propertyDTO.getPropertyType()).willReturn(PropertyType.getStringFromType(A_PROPERTY_TYPE));
		given(propertyDTO.getPropertyStatus()).willReturn(PropertyStatus.getStringFromStatus(A_PROPERTY_STATUS));
		given(propertyDTO.getPropertyFeatures()).willReturn(featuresDTO);
	}
}
