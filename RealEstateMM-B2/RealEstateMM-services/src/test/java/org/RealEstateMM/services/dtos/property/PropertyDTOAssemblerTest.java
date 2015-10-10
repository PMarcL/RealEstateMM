package org.RealEstateMM.services.dtos.property;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class PropertyDTOAssemblerTest {
	private final double DELTA = 0.001;
	private final double A_PRICE = 200000.00;
	private final PropertyStatus A_PROPERTY_STATUS = PropertyStatus.ONSALE;
	private final PropertyType A_PROPERTY_TYPE = PropertyType.HOUSE;
	private final String OWNER_PSEUDO = "John90";

	private PropertyAddress propertyAddress;
	private PropertyAddressDTO addressDTO;
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
		addressDTO = new PropertyAddressDTO();

		owner = mock(User.class);
		when(owner.getPseudonym()).thenReturn(OWNER_PSEUDO);
		when(userRepository.getUserWithPseudonym(OWNER_PSEUDO)).thenReturn(Optional.of(owner));

	}

	@Test
	public void givenAPropertyInformationsWhenBuildToDTOThenReturnedDTOShouldHaveTheSameInformations() {
		when(addressAssembler.toDTO(propertyAddress)).thenReturn(addressDTO);
		PropertyDTO propertyInfos = assembler.toDTO(property);

		assertEquals(PropertyType.getStringFromType(A_PROPERTY_TYPE), propertyInfos.getPropertyType());
		assertEquals(addressDTO, propertyInfos.getPropertyAddressDTO());
		assertEquals(A_PRICE, propertyInfos.getPropertyPrice(), DELTA);
		assertEquals(OWNER_PSEUDO, propertyInfos.getPropertyOwner());
		assertEquals(PropertyStatus.getStringFromStatus(A_PROPERTY_STATUS), propertyInfos.getPropertyStatus());
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenBuildTheAddress() {
		PropertyDTO dto = getConfiguredPropertyInformationsMock();

		assembler.fromDTO(dto);

		verify(addressAssembler).fromDTO(addressDTO);
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

		assertEquals(A_PROPERTY_TYPE, result.getType());
		assertEquals(dto.getPropertyPrice(), result.getPrice(), DELTA);
		assertEquals(A_PROPERTY_STATUS, result.getPropertyStatus());
		assertEquals(propertyAddress, result.getAddress());
	}

	private PropertyDTO getConfiguredPropertyInformationsMock() {
		PropertyDTO dto = mock(PropertyDTO.class);
		when(dto.getPropertyAddressDTO()).thenReturn(addressDTO);
		when(dto.getPropertyOwner()).thenReturn(OWNER_PSEUDO);
		when(dto.getPropertyType()).thenReturn(PropertyType.getStringFromType(A_PROPERTY_TYPE));
		when(dto.getPropertyStatus()).thenReturn(PropertyStatus.getStringFromStatus(A_PROPERTY_STATUS));
		return dto;
	}
}
