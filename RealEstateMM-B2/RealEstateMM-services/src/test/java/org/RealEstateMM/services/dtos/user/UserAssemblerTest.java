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

		assertEquals(userDTO.getPseudonym(), DefaultUserValue.PSEUDO);
		assertEquals(userDTO.getEmail(), DefaultUserValue.EMAIL);
		assertEquals(userDTO.getPhoneNumber(), DefaultUserValue.PHONE_NUMBER);
		assertEquals(userDTO.getFirstName(), DefaultUserValue.FIRST_NAME);
		assertEquals(userDTO.getLastName(), DefaultUserValue.LAST_NAME);
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameInfo() {
		UserDTO dto = assembler.toDTO(user);
		User result = assembler.fromDTO(dto);

		assertEquals(result.pseudonym, dto.getPseudonym());
		assertEquals(result.email.toString(), dto.getEmail());
		assertEquals(result.phoneNumber.toString(), dto.getPhoneNumber());
		assertEquals(result.firstName, dto.getFirstName());
		assertEquals(result.lastName, dto.getLastName());
	}
}
