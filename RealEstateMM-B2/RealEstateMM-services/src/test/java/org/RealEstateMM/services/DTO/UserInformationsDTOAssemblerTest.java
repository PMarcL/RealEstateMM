package org.RealEstateMM.services.DTO;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserInformations;
import org.junit.Before;
import org.junit.Test;

public class UserInformationsDTOAssemblerTest {

	private final String EMAIL = "example@hotmail.com";
	private final String NAME = "John Doe";
	private final String PHONE_NUMBER = "(819) 418-5739";
	private final String PSEUDO = "JohnD90";

	private UserInformationsDTOAssembler assembler;

	private UserInformations userInfo;
	private UserInformationsDTO userInfoDTO;

	@Before
	public void initialisation() {
		assembler = new UserInformationsDTOAssembler();
		userInfo = new UserInformations(PSEUDO, NAME, EMAIL, PHONE_NUMBER);
	}

	@Test
	public void givenAUserInformationsObjectWhenBuildDTOIsCalledThenReturnsDTOWithValidInformations() {
		userInfoDTO = assembler.buildDTO(userInfo);

		assertTrue(userInfoDTO.getEmail().equals(EMAIL));
		assertTrue(userInfoDTO.getPhoneNumber().equals(PHONE_NUMBER));
		assertTrue(userInfoDTO.getName().equals(NAME));
		assertTrue(userInfoDTO.getPseudonym().equals(PSEUDO));
	}
}
