package org.RealEstateMM.services;

import static org.junit.Assert.*;
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
	private final String A_PSEUDO = "pseudo34";
	private final String A_PASSWORD = "pw1234";
	private final String INVALID_PASSWORD = "posdf33";
	private final User A_USER = mock(User.class);

	private UserRepository userRepository;
	private UserAssembler userAssembler;

	private UserService userService;

	@Before
	public void setup() throws Exception {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);

		userService = new UserService(userRepository, userAssembler);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		when(userAssembler.fromDTO(A_USER_DTO)).thenReturn(A_USER);
		userService.create(A_USER_DTO);
		verify(userRepository).addUser(A_USER);
	}

	@Test
	public void givenAPseudonymWithRightPassWordWhenAuthenticateThenReturnTheUserDTO() throws Exception {
		when(userRepository.getUserWithPseudonym(A_PSEUDO)).thenReturn(Optional.of(A_USER));
		when(A_USER.validPassword(A_PASSWORD)).thenReturn(true);
		when(userAssembler.toDTO(A_USER)).thenReturn(A_USER_DTO);

		UserDTO actual = userService.authenticate(A_PSEUDO, A_PASSWORD);

		assertEquals(A_USER_DTO, actual);
	}

	@Test(expected = UserDoesNotExistException.class)
	public void givenNoUserWhenAuthenticateThenThrowUserNotFoundException() throws Exception {
		when(userRepository.getUserWithPseudonym(A_PSEUDO)).thenReturn(Optional.empty());
		userService.authenticate(A_PSEUDO, A_PASSWORD);
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenAuthenticateThrowInvalidPasswordException() throws Exception {
		when(userRepository.getUserWithPseudonym(A_PSEUDO)).thenReturn(Optional.of(A_USER));
		when(A_USER.validPassword(A_PASSWORD)).thenReturn(true);

		userService.authenticate(A_PSEUDO, INVALID_PASSWORD);
	}

}
