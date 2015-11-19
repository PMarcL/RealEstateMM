package org.RealEstateMM.services.user.dtos;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRole;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {
	private final String USER_ROLE_DESCRIPTION = "userRole";

	private UserAssembler assembler;

	private UserRoleFactory roleFactory;

	@Before
	public void setup() {
		roleFactory = mock(UserRoleFactory.class);
		assembler = new UserAssembler(roleFactory);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		UserDTO dto = assembler.toDTO(new UserBuilder().build());

		assertEquals(UserBuilder.DEFAULT_PSEUDONYM, dto.getPseudonym());
		assertEquals(UserBuilder.DEFAULT_EMAIL_ADDRESS, dto.getEmailAddress());
		assertEquals(UserBuilder.DEFAULT_PHONE_NUMBER, dto.getPhoneNumber());
		assertEquals(UserBuilder.DEFAULT_FIRST_NAME, dto.getFirstName());
		assertEquals(UserBuilder.DEFAULT_LAST_NAME, dto.getLastName());
		assertEquals(UserBuilder.DEFAULT_PASSWORD, dto.getPassword());
		assertEquals(UserBuilder.DEFAULT_USER_ROLE.toString(), dto.getUserRole());
	}

	@Test
	public void givenADTOWhenAssembleThenReturnAUserWithSamePseudonym() {
		UserDTO dto = createDTO();
		User result = assembler.fromDTO(dto);
		assertEquals(UserBuilder.DEFAULT_PSEUDONYM, result.getPseudonym());
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameUserInformations() {
		UserDTO dto = createDTO();

		User result = assembler.fromDTO(dto);

		UserInformations userInfo = result.getUserInformations();
		assertEquals(dto.getEmailAddress(), userInfo.emailAddress);
		assertEquals(dto.getPhoneNumber(), userInfo.phoneNumber);
		assertEquals(dto.getFirstName(), userInfo.firstName);
		assertEquals(dto.getLastName(), userInfo.lastName);
		assertEquals(dto.getPassword(), userInfo.password);
	}

	@Test
	public void givenADTOWhenAssembleThenUseFactoryToCreateUserRole() {
		UserDTO dto = createDTO();
		assembler.fromDTO(dto);
		verify(roleFactory).create(USER_ROLE_DESCRIPTION);
	}

	@Test
	public void givenADTOWhenAssembleThenCreateUserWithUserRoleFromFactory() {
		UserDTO dto = createDTO();
		UserRole role = mock(UserRole.class);
		given(role.getRoleDescription()).willReturn(AccessLevel.ADMIN);
		given(roleFactory.create(USER_ROLE_DESCRIPTION)).willReturn(role);

		User result = assembler.fromDTO(dto);

		assertEquals(AccessLevel.ADMIN, result.getRoleDescription());
	}

	private UserDTO createDTO() {
		UserDTO dto = new UserDTO(UserBuilder.DEFAULT_PSEUDONYM, UserBuilder.DEFAULT_PASSWORD,
				UserBuilder.DEFAULT_FIRST_NAME, UserBuilder.DEFAULT_LAST_NAME, UserBuilder.DEFAULT_EMAIL_ADDRESS,
				UserBuilder.DEFAULT_PHONE_NUMBER, USER_ROLE_DESCRIPTION);
		return dto;
	}

}
