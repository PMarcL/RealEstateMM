package org.RealEstateMM.services.dto;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class UserInformationsAssemblerTest {

	private final String PSEUDO = "JohnD90";
	private final String PASSWORD = "potato123";
	private final String EMAIL = "example@hotmail.com";
	private final String FIRSTNAME = "John";
	private final String LASTNAME = "Doe";
	private final String PHONE_NUMBER = "(819) 418-5739";

	private UserInformationsAssembler assembler;

	private User user;
	private UserInformations userInfo;

	@Before
	public void initialisation() {
		assembler = new UserInformationsAssembler();
		user = new User(PSEUDO, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		userInfo = assembler.toDTO(user);

		assertEquals(userInfo.getPseudonym(), PSEUDO);
		assertEquals(userInfo.getEmail(), EMAIL);
		assertEquals(userInfo.getPhoneNumber(), PHONE_NUMBER);
		assertEquals(userInfo.getFirstName(), FIRSTNAME);
		assertEquals(userInfo.getLastName(), LASTNAME);
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameInfo() {
		UserInformations dto = assembler.toDTO(user);
		User result = assembler.fromDTO(dto);

		assertEquals(result.pseudonym, dto.getPseudonym());
		assertEquals(result.email.toString(), dto.getEmail());
		assertEquals(result.phoneNumber.toString(), dto.getPhoneNumber());
		assertEquals(result.firstName, dto.getFirstName());
		assertEquals(result.lastName, dto.getLastName());
	}
}
