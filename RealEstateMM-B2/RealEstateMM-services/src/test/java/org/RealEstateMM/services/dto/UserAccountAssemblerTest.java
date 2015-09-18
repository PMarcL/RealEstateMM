package org.RealEstateMM.services.dto;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.UserAccount;
import org.junit.Before;
import org.junit.Test;

public class UserAccountAssemblerTest {

	private final Email EMAIL = new Email("example@hotmail.com");
	private final String NAME = "John Doe";
	private final PhoneNumber PHONE_NUMBER = new PhoneNumber("(819) 418-5739");
	private final String PSEUDO = "JohnD90";

	private UserAccountAssembler assembler;

	private UserAccount userAccount;
	private UserInformations userInfo;

	@Before
	public void initialisation() {
		assembler = new UserAccountAssembler();
		userAccount = new UserAccount(PSEUDO, NAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOIsCalledThenReturnsDTOWithValidInformations() {
		userInfo = assembler.buildDTO(userAccount);

		assertTrue(userInfo.email.equals(EMAIL.toString()));
		assertTrue(userInfo.phoneNumber.equals(PHONE_NUMBER.toString()));
		assertTrue(userInfo.name.equals(NAME));
		assertTrue(userInfo.pseudonym.equals(PSEUDO));
	}
}
