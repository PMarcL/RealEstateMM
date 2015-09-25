package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.Optional;
import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private final User A_USER = new DefaultUserBuilder().build();
	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();
	private final String PSEUDO = "pseudo34";

	private UserRepository userRepository;
	private UserAssembler userAssembler;

	private UserService accountService;

	@Before
	public void setup() throws Exception {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);

		accountService = new UserService(userRepository, userAssembler);
	}

	@Test
	public void whenCreateUserAsAnonymousThenUseAssemblerToCreateUser() {
		accountService.createUser(A_USER_DTO);
		verify(userAssembler).fromDTO(A_USER_DTO);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		when(userAssembler.fromDTO(A_USER_DTO)).thenReturn(A_USER);
		accountService.createUser(A_USER_DTO);
		verify(userRepository).addUser(A_USER);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceThenCallsTheRepository() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		accountService.userExists(PSEUDO);
		verify(userRepository).getUserWithPseudonym(PSEUDO);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfNoUserExistsThenReturnsFalse() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		assertFalse(accountService.userExists(PSEUDO));
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfUserExistsThenReturnsTrue() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(A_USER));
		assertTrue(accountService.userExists(PSEUDO));
	}
}
