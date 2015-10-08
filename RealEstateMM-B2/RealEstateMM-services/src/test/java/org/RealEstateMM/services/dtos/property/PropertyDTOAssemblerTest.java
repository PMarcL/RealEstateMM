package org.RealEstateMM.services.dtos.property;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class PropertyDTOAssemblerTest {
	private final double DELTA = 0.001;
	private final double A_PRICE = 200000.00;
	private final String A_PROPERTY_STATUS = "On sale";
	private final String A_PROPERTY_TYPE = "Residential";
	private final String OWNER_PSEUDO = "John90";

	private PropertyAddress propertyAddress;
	private PropertyAddressDTO addressInformations;
	private Property property;
	private PropertyAddressDTOAssembler addressAssembler;
	private UserRepository userRepository;
	private User owner;
	private PropertyDTOAssembler assembler;
	
	

	@Before
	public void setup() {
		addressAssembler = mock(PropertyAddressDTOAssembler.class);
		userRepository = mock(UserRepository.class);
		
		assembler = new PropertyDTOAssembler(addressAssembler, userRepository);
		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PRICE, OWNER_PSEUDO, A_PROPERTY_STATUS);
		addressInformations = new PropertyAddressDTO();
		
		owner = mock(User.class);
		when(owner.getPseudonym()).thenReturn(OWNER_PSEUDO);
		when(userRepository.getUserWithPseudonym(OWNER_PSEUDO)).thenReturn(Optional.of(owner));

	}

	@Test
	public void givenAPropertyInformationsWhenBuildToDTOThenReturnedDTOShouldHaveTheSameInformations() {
		when(addressAssembler.toDTO(propertyAddress)).thenReturn(addressInformations);
		PropertyDTO propertyInfos = assembler.toDTO(property);

		assertEquals(A_PROPERTY_TYPE, propertyInfos.getPropertyType());
		assertEquals(addressInformations, propertyInfos.getPropertyAddressInformations());
		assertEquals(A_PRICE, propertyInfos.getPropertyPrice(), DELTA);
		assertEquals(OWNER_PSEUDO, propertyInfos.getPropertyOwner());
		assertEquals(A_PROPERTY_STATUS, propertyInfos.getPropertyStatus());
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenBuildTheAddress() {
		PropertyDTO dto = getConfiguredPropertyInformationsMock();

		assembler.fromDTO(dto);

		verify(addressAssembler).fromDTO(addressInformations);
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenGetTheUserFromRepository() {
		PropertyDTO dto = getConfiguredPropertyInformationsMock();

		assembler.fromDTO(dto);

		verify(userRepository).getUserWithPseudonym(OWNER_PSEUDO);
	}

	@Test
	public void givenAPropertyDTOWhenBuildingFromDTOThenPropertyShouldHaveTheSameInformations() {
		PropertyDTO dto = assembler.toDTO(property);
		Property result = assembler.fromDTO(dto);

		assertEquals(dto.getPropertyType(), result.propertyType);
		assertEquals(dto.getPropertyPrice(), result.propertyPrice, DELTA);
		assertEquals(dto.getPropertyStatus(), result.propertyStatus);
	}

	private PropertyDTO getConfiguredPropertyInformationsMock() {
		PropertyDTO dto = mock(PropertyDTO.class);
		when(dto.getPropertyAddressInformations()).thenReturn(addressInformations);
		when(dto.getPropertyOwner()).thenReturn(OWNER_PSEUDO);
		return dto;
	}
}
