package org.RealEstateMM.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.RealEstateMM.domain.builders.DefaultUserBuilder;
import org.RealEstateMM.domain.models.session.Session;
import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.domain.repositories.ExistingPseudoException;
import org.RealEstateMM.domain.repositories.UserRepository;
import org.RealEstateMM.services.assemblers.UserAssembler;
import org.RealEstateMM.services.dtos.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class UserRegistrationServiceTest {

	private final String SOME_TOKEN = "some token";
	private final String UNIQUE_PSEUDO = "MyCoolPseudo";
	private final String EXISTING_PSEUDO = "MyTooCommonPseudo";

	private final User UNIQUE_PSEUDO_USER = createEserWithPseudo(UNIQUE_PSEUDO);
	private final User EXISTING_PSEUDO_USER = createEserWithPseudo(EXISTING_PSEUDO);

	private final UserDTO EXISTING_PSEUDO_USER_DTO = new UserAssembler().buildDTO(EXISTING_PSEUDO_USER);
	private final UserDTO UNIQUE_PSEUDO_USER_DTO = new UserAssembler().buildDTO(UNIQUE_PSEUDO_USER);

	private UserRepository userRepoMock;
	private UserAssembler userAssemblerMock;
	private UserRegistrationService service;
	private SessionService sessionService;

	@Before
	public void setUp() {
		userRepoMock = mock(UserRepository.class);
		userAssemblerMock = mock(UserAssembler.class);
		sessionService = mock(SessionService.class);

		when(userAssemblerMock.assemble(UNIQUE_PSEUDO_USER_DTO)).thenReturn(UNIQUE_PSEUDO_USER);
		when(userAssemblerMock.assemble(EXISTING_PSEUDO_USER_DTO)).thenReturn(EXISTING_PSEUDO_USER);
		when(userRepoMock.addUser(UNIQUE_PSEUDO_USER)).thenReturn(UNIQUE_PSEUDO_USER);
		when(userRepoMock.addUser(EXISTING_PSEUDO_USER)).thenThrow(new ExistingPseudoException(EXISTING_PSEUDO));

		when(userAssemblerMock.buildDTO(UNIQUE_PSEUDO_USER)).thenReturn(UNIQUE_PSEUDO_USER_DTO);

		Session newSession = new Session(UNIQUE_PSEUDO_USER, SOME_TOKEN);
		when(sessionService.createSession(UNIQUE_PSEUDO_USER)).thenReturn(newSession);

		service = new UserRegistrationService(userRepoMock, sessionService, userAssemblerMock);
	}

	@Test
	public void givenANewUserWithUniquePseudoWhenRegisterThenCreateTheUserOnce() {
		service.register(UNIQUE_PSEUDO_USER_DTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(userRepoMock, times(1)).addUser(captor.capture());
		assertEquals(UNIQUE_PSEUDO_USER, captor.getValue());
	}

	@Test(expected = ExistingPseudoException.class)
	public void givenANewUserWithAnAlreadyExistingPseudoWhenRegisterThenThrowsExistingPseudoException() {
		service.register(EXISTING_PSEUDO_USER_DTO);
	}

	@Test
	public void givenANewUserWithUniquePseudoWhenRegisterThenCreateASession() {
		service.register(UNIQUE_PSEUDO_USER_DTO);

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(sessionService, times(1)).createSession(captor.capture());
		assertEquals(UNIQUE_PSEUDO_USER, captor.getValue());
	}

	@Test(expected = ExistingPseudoException.class)
	public void givenANewUserWithAnAlreadyExistingPseudoWhenRegisterThenDontCreateSession() {
		service.register(EXISTING_PSEUDO_USER_DTO);
		verifyNoMoreInteractions(sessionService);
	}

	@Test
	public void givenANewUserWithAnUniquePseudoWhenRegisterThenReturnUserDTOWithSessionToken() {
		UserDTO actual = service.register(UNIQUE_PSEUDO_USER_DTO);

		UserDTO expected = new UserAssembler().buildDTO(UNIQUE_PSEUDO_USER);
		expected.setToken(SOME_TOKEN);
		assertEquals(expected, actual);
	}

	private User createEserWithPseudo(String pseudo) {
		return new DefaultUserBuilder().withPseudonym(pseudo).build();
	}
}
