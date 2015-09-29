package org.RealEstateMM.services.dtos.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeFactory;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.servicelocator.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

	private UserAssembler assembler;

	private User user;
	private UserDTO userDTO;
	private UserTypeFactory factory;

	@Before
	public void setup() {
		factory = mock(UserTypeFactory.class);
		when(factory.makeUserType(UserBuilder.DEFAULT_USER_TYPE_DESC)).thenReturn(UserType.SELLER);
		ServiceLocator.getInstance().registerService(UserTypeFactory.class, factory);
		assembler = new UserAssembler();
		user = new UserBuilder().build();
	}

	@After
	public void clean() {
		ServiceLocator.getInstance().clearAllServices();
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		userDTO = assembler.toDTO(user);

		assertEquals(UserBuilder.DEFAULT_PSEUDO, userDTO.getPseudonym());
		assertEquals(UserBuilder.DEFAULT_EMAIL, userDTO.getEmail());
		assertEquals(UserBuilder.DEFAULT_PHONE_NUMBER, userDTO.getPhoneNumber());
		assertEquals(UserBuilder.DEFAULT_FIRST_NAME, userDTO.getFirstName());
		assertEquals(UserBuilder.DEFAULT_LAST_NAME, userDTO.getLastName());
		assertEquals(UserBuilder.DEFAULT_PASSWORD, userDTO.getPassword());
		assertEquals(UserBuilder.DEFAULT_USER_TYPE_DESC, userDTO.getUserType());
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameInfo() {
		UserDTO dto = assembler.toDTO(user);
		User result = assembler.fromDTO(dto);

		assertEquals(dto.getPseudonym(), result.getPseudonym());
		assertEquals(dto.getEmail(), result.getEmail());
		assertEquals(dto.getPhoneNumber(), result.getPhoneNumber());
		assertEquals(dto.getFirstName(), result.getFirstName());
		assertEquals(dto.getLastName(), result.getLastName());
		assertEquals(dto.getPassword(), result.getPassword());
		assertEquals(dto.getUserType(), result.getUserTypeDescription());
	}

}
