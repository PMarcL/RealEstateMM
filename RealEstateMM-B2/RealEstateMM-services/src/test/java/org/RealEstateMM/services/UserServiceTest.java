package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import java.util.Optional;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();
	private final String PSEUDO = "pseudo34";
	private final String PASSWORD = "pw1234";

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private User user;

	private UserService accountService;

	@Before
	public void setup() throws Exception {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);
		user = mock(User.class);

		accountService = new UserService(userRepository, userAssembler);
	}

	@Test
	public void whenCreateUserAsAnonymousThenUseAssemblerToCreateUser() {
		accountService.createUser(A_USER_DTO);
		verify(userAssembler).fromDTO(A_USER_DTO);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		when(userAssembler.fromDTO(A_USER_DTO)).thenReturn(user);
		accountService.createUser(A_USER_DTO);
		verify(userRepository).addUser(user);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceThenCallsTheRepository() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		accountService.userExists(PSEUDO, PASSWORD);
		verify(userRepository).getUserWithPseudonym(PSEUDO);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfNoUserExistsThenReturnsFalse() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		assertFalse(accountService.userExists(PSEUDO, PASSWORD));
	}

	@Test
	public void givenAValidPseudonymWhenCheckingForUserExistanceThenChecksIfUserHasPassword() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		accountService.userExists(PSEUDO, PASSWORD);
		verify(user).hasPassword(PASSWORD);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfUserExistsThenReturnsTrue() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		when(user.hasPassword(PASSWORD)).thenReturn(true);
		assertTrue(accountService.userExists(PSEUDO, PASSWORD));
	}
}
