package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import java.util.Optional;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();
	private final String PSEUDO = "pseudo34";
	private final String PASSWORD = "pw1234";
	private final String INVALID_PASSWORD = "posdf33";
	private static final boolean HAS_PASSWORD = true;

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
	public void givenAPseudonymWhenfindingUserTypeThenCallsTheRepository() throws Exception {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		when(user.hasPassword(PASSWORD)).thenReturn(HAS_PASSWORD);

		accountService.findUserType(PSEUDO, PASSWORD);

		verify(userRepository).getUserWithPseudonym(PSEUDO);
	}

	@Test(expected = UserDoesNotExistException.class)
	public void givenNoUserWhenFindingUserTypeThenThrowUserNotFoundException() throws Exception {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());

		accountService.findUserType(PSEUDO, PASSWORD);
	}

	@Test
	public void givenAValidPseudonymWhenCheckingForUserExistanceThenChecksIfUserHasPassword() throws Exception {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		when(user.hasPassword(PASSWORD)).thenReturn(HAS_PASSWORD);

		accountService.findUserType(PSEUDO, PASSWORD);

		verify(user).hasPassword(PASSWORD);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfUserExistsThenReturnsTrue() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		when(user.hasPassword(PASSWORD)).thenReturn(HAS_PASSWORD);
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenFindingUserTypeThrowInvalidPasswordException() throws Exception {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(user));
		when(user.hasPassword(PASSWORD)).thenReturn(HAS_PASSWORD);

		accountService.findUserType(PSEUDO, INVALID_PASSWORD);
	}
}
