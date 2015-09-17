package org.RealEstateMM.services.dto;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserAccountAssembler;
import org.junit.Before;
import org.junit.Test;

public class UserAccountAssemblerTest {

	private final String EMAIL = "example@hotmail.com";
	private final String NAME = "John Doe";
	private final String PHONE_NUMBER = "(819) 418-5739";
	private final String PSEUDO = "JohnD90";

	private UserAccountAssembler assembler;

	private UserAccount userInfo;
	private UserInformations userInfoDTO;

	@Before
	public void initialisation() {
		assembler = new UserAccountAssembler();
		userInfo = new UserAccount(PSEUDO, NAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOIsCalledThenReturnsDTOWithValidInformations() {
		userInfoDTO = assembler.buildDTO(userInfo);

		assertTrue(userInfoDTO.email.equals(EMAIL));
		assertTrue(userInfoDTO.phoneNumber.equals(PHONE_NUMBER));
		assertTrue(userInfoDTO.name.equals(NAME));
		assertTrue(userInfoDTO.pseudonym.equals(PSEUDO));
	}
}
