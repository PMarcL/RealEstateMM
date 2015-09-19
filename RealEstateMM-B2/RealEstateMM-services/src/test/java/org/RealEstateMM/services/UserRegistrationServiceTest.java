package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.RealEstateMM.domain.builders.UserBuilder;
import org.RealEstateMM.domain.user.Email;
import org.RealEstateMM.domain.user.Name;
import org.RealEstateMM.domain.user.PhoneNumber;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserRegistrationServiceTest {

	private final String A_PSEUDO = "MyCoolName";
	private final Name A_NAME = new Name("John", "Doe");
	private final Email A_EMAIL = new Email("myEmail@something.ca");
	private final PhoneNumber A_PHONE_NUM = new PhoneNumber("418-656-7766");

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void givenANewUserWithUniquePseudonymWhenRegisterThenCreateTheUserOnce() {
		User aUserWithUniquePseudo = aUser();
		UserDTO userDTO = new UserAssembler().buildDTO(aUserWithUniquePseudo);

		UserRepository userRepoMock = mock(UserRepository.class);
		UserAssembler userAssemblerMock = mock(UserAssembler.class);
		when(userAssemblerMock.assemble(userDTO)).thenReturn(aUserWithUniquePseudo);
		UserRegistrationService service = new UserRegistrationService(userRepoMock, userAssemblerMock);

		service.register(userDTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepoMock, times(1)).create(captor.capture());
		assertEquals(aUserWithUniquePseudo, captor.getValue());
	}

	@Test(expected = ExistingPseudoException.class)
	public void givenANewUserWithAnAlreadyExistingPseudonymWhenRegisterThenThrowsExistingPseudoException() {
		User aUserWithExistingPseudo = aUser();
		UserDTO userDTO = new UserAssembler().buildDTO(aUserWithExistingPseudo);

		UserRepository userRepoMock = mock(UserRepository.class);
		UserAssembler userAssemblerMock = mock(UserAssembler.class);
		when(userAssemblerMock.assemble(userDTO)).thenReturn(aUserWithExistingPseudo);
		when(userRepoMock.create(aUserWithExistingPseudo)).thenThrow(new ExistingPseudoException(userDTO.pseudonym));
		UserRegistrationService userRegistrationService = new UserRegistrationService(userRepoMock, userAssemblerMock);

		userRegistrationService.register(userDTO);
	}

	private User aUser() {
		return new UserBuilder(A_PSEUDO, A_NAME, A_EMAIL, A_PHONE_NUM).build();
	}

}
