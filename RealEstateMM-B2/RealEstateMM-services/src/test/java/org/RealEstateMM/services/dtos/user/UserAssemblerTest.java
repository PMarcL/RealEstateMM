package org.RealEstateMM.services.dtos.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.helpers.DefaultUserValue;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

	private UserAssembler assembler;

	private User user;
	private UserDTO userDTO;

	@Before
	public void initialisation() {
		assembler = new UserAssembler();
		user = new DefaultUserBuilder().build();
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		userDTO = assembler.toDTO(user);

		assertEquals(DefaultUserValue.PSEUDO, userDTO.getPseudonym());
		assertEquals(DefaultUserValue.EMAIL, userDTO.getEmail());
		assertEquals(DefaultUserValue.PHONE_NUMBER, userDTO.getPhoneNumber());
		assertEquals(DefaultUserValue.FIRST_NAME, userDTO.getFirstName());
		assertEquals(DefaultUserValue.LAST_NAME, userDTO.getLastName());
		assertEquals(DefaultUserValue.PASSWORD, userDTO.getPassword());
		assertEquals(DefaultUserValue.USER_TYPE_DESC, userDTO.getUserType());
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
