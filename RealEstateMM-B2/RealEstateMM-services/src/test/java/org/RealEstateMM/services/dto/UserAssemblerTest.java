package org.RealEstateMM.services.dto;

import static org.junit.Assert.assertEquals;

import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.Name;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

	private final Email EMAIL = new Email("example@hotmail.com");
	private final Name NAME = new Name("John", "Doe");
	private final PhoneNumber PHONE_NUMBER = new PhoneNumber("(819) 418-5739");
	private final String PSEUDO = "JohnD90";

	private UserAssembler assembler;

	private User userAccount;
	private UserDTO userInfo;

	@Before
	public void initialisation() {
		assembler = new UserAssembler();
		userAccount = new User(PSEUDO, NAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOIsCalledThenReturnsDTOWithValidInformations() {
		userInfo = assembler.buildDTO(userAccount);

		assertEquals(userInfo.pseudonym, PSEUDO);
		assertEquals(userInfo.email, EMAIL.toString());
		assertEquals(userInfo.phoneNumber, PHONE_NUMBER.toString());
		assertEquals(userInfo.firstName, NAME.firstName);
		assertEquals(userInfo.lastName, NAME.lastName);
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUser() {
		UserDTO dto = assembler.buildDTO(userAccount);
		User result = assembler.assemble(dto);

		assertEquals(result.pseudonym, dto.pseudonym);
		assertEquals(result.email.toString(), dto.email);
		assertEquals(result.phoneNumber.toString(), dto.phoneNumber);
		assertEquals(result.name.firstName, dto.firstName);
		assertEquals(result.name.lastName, dto.lastName);
	}
}
