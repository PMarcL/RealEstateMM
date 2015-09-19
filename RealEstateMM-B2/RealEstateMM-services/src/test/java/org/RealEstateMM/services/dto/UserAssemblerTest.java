package org.RealEstateMM.services.dto;

import static org.junit.Assert.assertEquals;

import org.RealEstateMM.domain.builders.UserBuilder;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;
import org.junit.Before;
import org.junit.Test;

public class UserAssemblerTest {

	private final String PSEUDO = "JohnD90";
	private final Email EMAIL = new Email("example@hotmail.com");
	private final Name NAME = new Name("John", "Doe");
	private final PhoneNumber PHONE_NUMBER = new PhoneNumber("(819) 418-5739");

	private UserAssembler assembler;

	private User userAccount;
	private UserDTO userInfo;

	@Before
	public void initialisation() {
		assembler = new UserAssembler();
		userAccount = new UserBuilder().withPseudonym(PSEUDO).withName(NAME).withEmail(EMAIL)
				.withPhoneNumber(PHONE_NUMBER).build();
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOThenReturnsDTOWithSameInformations() {
		userInfo = assembler.buildDTO(userAccount);

		assertEquals(userInfo.pseudonym, PSEUDO);
		assertEquals(userInfo.email, EMAIL.toString());
		assertEquals(userInfo.phoneNumber, PHONE_NUMBER.toString());
		assertEquals(userInfo.firstName, NAME.firstName);
		assertEquals(userInfo.lastName, NAME.lastName);
	}

	@Test
	public void givenADTOWhenAssembleThenReturnADomainObjectUserWithSameInfo() {
		UserDTO dto = assembler.buildDTO(userAccount);
		User result = assembler.assemble(dto);

		assertEquals(result.pseudonym, dto.pseudonym);
		assertEquals(result.email.toString(), dto.email);
		assertEquals(result.phoneNumber.toString(), dto.phoneNumber);
		assertEquals(result.name.firstName, dto.firstName);
		assertEquals(result.name.lastName, dto.lastName);
	}
}
