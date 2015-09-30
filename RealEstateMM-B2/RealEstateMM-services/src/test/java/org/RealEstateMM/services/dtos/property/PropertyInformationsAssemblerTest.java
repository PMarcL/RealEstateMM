package org.RealEstateMM.services.dtos.property;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.property.PropertyInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformationsAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyInformationsAssemblerTest {
	private final double DELTA = 0.001;
	private final double A_PRICE = 200000.00;
	private final String A_PROPERTY_STATUS = "On sale";
	private final String A_PROPERTY_TYPE = "Residential";
	private final String OWNER_PSEUDO = "John90";

	private PropertyAddress propertyAddress;
	private PropertyAddressInformations addressInformations;
	private User owner;
	private Property property;
	private PropertyAddressInformationsAssembler addressAssembler;
	private UserRepository userRepository;

	private PropertyInformationsAssembler assembler;

	@Before
	public void setup() {
		addressAssembler = mock(PropertyAddressInformationsAssembler.class);
		userRepository = mock(UserRepository.class);
		owner = mock(User.class);
		assembler = new PropertyInformationsAssembler(addressAssembler, userRepository);
		property = new Property(A_PROPERTY_TYPE, propertyAddress, A_PRICE, owner, A_PROPERTY_STATUS);
		addressInformations = new PropertyAddressInformations();

		when(owner.getPseudonym()).thenReturn(OWNER_PSEUDO);
		when(userRepository.getUserWithPseudonym(OWNER_PSEUDO)).thenReturn(Optional.of(owner));
	}

	@Test
	public void givenAPropertyInformationsWhenBuildToDTOThenReturnedDTOShouldHaveTheSameInformations() {
		when(addressAssembler.toDTO(propertyAddress)).thenReturn(addressInformations);
		PropertyInformations propertyInfos = assembler.toDTO(property);

		assertEquals(A_PROPERTY_TYPE, propertyInfos.getPropertyType());
		assertEquals(addressInformations, propertyInfos.getPropertyAddressInformations());
		assertEquals(A_PRICE, propertyInfos.getPropertyPrice(), DELTA);
		assertEquals(OWNER_PSEUDO, propertyInfos.getPropertyOwner());
		assertEquals(A_PROPERTY_STATUS, propertyInfos.getPropertyStatus());
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenBuildTheAddress() {
		PropertyInformations dto = getConfiguredPropertyInformationsMock();

		assembler.fromDTO(dto);

		verify(addressAssembler).fromDTO(addressInformations);
	}

	@Test
	public void givenAPropertyWhenBuildingFromDTOThenGetTheUserFromRepository() {
		PropertyInformations dto = getConfiguredPropertyInformationsMock();

		assembler.fromDTO(dto);

		verify(userRepository).getUserWithPseudonym(OWNER_PSEUDO);
	}

	@Test
	public void givenAPropertyDTOWhenBuildingFromDTOThenPropertyShouldHaveTheSameInformations() {
		PropertyInformations dto = assembler.toDTO(property);
		Property result = assembler.fromDTO(dto);

		assertEquals(dto.getPropertyType(), result.propertyType);
		assertEquals(dto.getPropertyPrice(), result.propertyPrice, DELTA);
		assertEquals(dto.getPropertyStatus(), result.propertyStatus);
	}

	private PropertyInformations getConfiguredPropertyInformationsMock() {
		PropertyInformations dto = mock(PropertyInformations.class);
		when(dto.getPropertyAddressInformations()).thenReturn(addressInformations);
		when(dto.getPropertyOwner()).thenReturn(OWNER_PSEUDO);
		return dto;
	}
}
