package org.RealEstateMM.services.dto;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.informations.Email;
import org.RealEstateMM.domain.user.informations.Name;
import org.RealEstateMM.domain.user.informations.PhoneNumber;
import org.junit.Before;
import org.junit.Test;

public class UserInformationsAssemblerTest {

	private final String PSEUDO = "JohnD90";
	private final String PASSWORD = "potato123";
	private final Email EMAIL = new Email("example@hotmail.com");
	private final Name NAME = new Name("John", "Doe");
	private final PhoneNumber PHONE_NUMBER = new PhoneNumber("(819) 418-5739");

	private UserInformationsAssembler assembler;

	private User user;
	private UserInformations userInfo;

	@Before
	public void initialisation() {
		assembler = new UserInformationsAssembler();
		user = new User(PSEUDO, PASSWORD, NAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		userInfo = assembler.toDTO(user);

		assertEquals(userInfo.pseudonym, PSEUDO);
		assertEquals(userInfo.email, EMAIL.toString());
		assertEquals(userInfo.phoneNumber, PHONE_NUMBER.toString());
		assertEquals(userInfo.firstName, NAME.firstName);
		assertEquals(userInfo.lastName, NAME.lastName);
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameInfo() {
		UserInformations dto = assembler.toDTO(user);
		User result = assembler.fromDTO(dto);

		assertEquals(result.pseudonym, dto.pseudonym);
		assertEquals(result.email.toString(), dto.email);
		assertEquals(result.phoneNumber.toString(), dto.phoneNumber);
		assertEquals(result.name.firstName, dto.firstName);
		assertEquals(result.name.lastName, dto.lastName);
	}
}
