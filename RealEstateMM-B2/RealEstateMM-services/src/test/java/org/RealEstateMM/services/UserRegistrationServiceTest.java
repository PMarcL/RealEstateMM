package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.UserRepository;
import org.RealEstateMM.services.dto.UserAssembler;
import org.RealEstateMM.services.dto.UserDTO;
import org.RealEstateMM.testdata.DefaultUserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserRegistrationServiceTest {

	private final String UNIQUE_PSEUDO = "MyCoolPseudo";
	private final String EXISTING_PSEUDO = "MyTooCommonPseudo";

	private final User UNIQUE_PSEUDO_USER = userWithPseudo(UNIQUE_PSEUDO);
	private final User EXISTING_PSEUDO_USER = userWithPseudo(EXISTING_PSEUDO);

	private final UserDTO EXISTING_PSEUDO_USER_DTO = new UserAssembler().buildDTO(EXISTING_PSEUDO_USER);
	private final UserDTO UNIQUE_PSEUDO_USER_DTO = new UserAssembler().buildDTO(UNIQUE_PSEUDO_USER);

	private UserRepository userRepoMock;
	private UserAssembler userAssemblerMock;
	private UserRegistrationService service;

	@Before
	public void setUp() {
		userRepoMock = mock(UserRepository.class);
		userAssemblerMock = mock(UserAssembler.class);

		when(userAssemblerMock.assemble(UNIQUE_PSEUDO_USER_DTO)).thenReturn(UNIQUE_PSEUDO_USER);
		when(userAssemblerMock.assemble(EXISTING_PSEUDO_USER_DTO)).thenReturn(EXISTING_PSEUDO_USER);
		when(userRepoMock.create(EXISTING_PSEUDO_USER)).thenThrow(new ExistingPseudoException(EXISTING_PSEUDO));

		service = new UserRegistrationService(userRepoMock, userAssemblerMock);

	}

	@Test
	public void givenANewUserWithUniquePseudonymWhenRegisterThenCreateTheUserOnce() {
		service.register(UNIQUE_PSEUDO_USER_DTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepoMock, times(1)).create(captor.capture());
		assertEquals(UNIQUE_PSEUDO_USER, captor.getValue());
	}

	@Test(expected = ExistingPseudoException.class)
	public void givenANewUserWithAnAlreadyExistingPseudoWhenRegisterThenThrowsExistingPseudoException() {
		service.register(EXISTING_PSEUDO_USER_DTO);
	}

	@Test
	public void givenANewUserWithAnAlreadyExistingPseudoWhenRegisterThenDontCreateSession() {

	}

	private User userWithPseudo(String pseudo) {
		return new DefaultUserBuilder().withPseudonym(pseudo).build();
	}
}
