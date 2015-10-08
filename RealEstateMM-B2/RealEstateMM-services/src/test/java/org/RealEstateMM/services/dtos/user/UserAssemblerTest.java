package org.RealEstateMM.services.dtos.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

	private UserAssembler assembler;

	private User user;
	private UserDTO userDTO;

	@Before
	public void setup() {
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

		UserInformations userInfo = result.getUserInformations();
		assertEquals(dto.getPseudonym(), result.getPseudonym());
		assertEquals(dto.getEmail(), userInfo.emailAddress);
		assertEquals(dto.getPhoneNumber(), userInfo.phoneNumber);
		assertEquals(dto.getFirstName(), userInfo.firstName);
		assertEquals(dto.getLastName(), userInfo.lastName);
		assertEquals(dto.getPassword(), userInfo.password);
		assertEquals(dto.getUserType(), result.getUserTypeDescription());
	}

}
