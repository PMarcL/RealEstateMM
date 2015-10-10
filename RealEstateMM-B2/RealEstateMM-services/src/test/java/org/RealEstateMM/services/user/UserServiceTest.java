package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.emailconfirmation.AlreadyConfirmedEmailAddressException;
import org.RealEstateMM.domain.user.emailconfirmation.EmailConfirmer;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.user.exceptions.InvalidPasswordException;
import org.RealEstateMM.services.user.exceptions.UserDoesNotExistException;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private static final String AN_INVALID_CONFIRMATION_CODE = "anInvalidCode";
	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();
	private final String A_PSEUDO = "pseudo34";
	private final String A_PASSWORD = "pw1234";
	private final String INVALID_PASSWORD = "posdf33";
	private final User A_USER = mock(User.class);

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private EmailConfirmer emailConfirmer;

	private UserService userService;

	@Before
	public void setup() throws Exception {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);
		emailConfirmer = mock(EmailConfirmer.class);

		given(userAssembler.fromDTO(A_USER_DTO)).willReturn(A_USER);

		userService = new UserService(userRepository, userAssembler, emailConfirmer);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		userService.create(A_USER_DTO);
		verify(userRepository).addUser(A_USER);
	}

	@Test
	public void whenCreateUserThenSendEmailConfirmationWithCreatedUserEmailConfirmationCode() {
		userService.create(A_USER_DTO);
		verify(emailConfirmer, times(1)).sendEmailConfirmation(A_USER);
	}

	@Test
	public void givenAPseudonymWithRightPassWordWhenAuthenticateThenReturnTheUserDTO() throws Exception {
		given(userRepository.getUserWithPseudonym(A_PSEUDO)).willReturn(Optional.of(A_USER));
		given(A_USER.hasPassword(A_PASSWORD)).willReturn(true);
		given(userAssembler.toDTO(A_USER)).willReturn(A_USER_DTO);

		UserDTO actual = userService.authenticate(A_PSEUDO, A_PASSWORD);

		assertEquals(A_USER_DTO, actual);
	}

	@Test(expected = UserDoesNotExistException.class)
	public void givenNoUserWhenAuthenticateThenThrowUserNotFoundException() throws Exception {
		given(userRepository.getUserWithPseudonym(A_PSEUDO)).willReturn(Optional.empty());
		userService.authenticate(A_PSEUDO, A_PASSWORD);
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenAuthenticateThrowInvalidPasswordException() throws Exception {
		given(userRepository.getUserWithPseudonym(A_PSEUDO)).willReturn(Optional.of(A_USER));
		given(A_USER.hasPassword(A_PASSWORD)).willReturn(true);

		userService.authenticate(A_PSEUDO, INVALID_PASSWORD);
	}

	@Test(expected = ImpossibleToConfirmEmailAddressException.class)
	public void givenAnInvalidConfirmationCodeWhenConfirmEmailThenThrowAnImpossibleToConfirmEmailAddressException()
			throws ImpossibleToConfirmEmailAddressException {

		doThrow(InvalidEmailConfirmationCodeException.class).when(emailConfirmer)
				.confirmEmailAddress(AN_INVALID_CONFIRMATION_CODE);
		userService.confirmEmailAddress(AN_INVALID_CONFIRMATION_CODE);
	}

	@Test(expected = ImpossibleToConfirmEmailAddressException.class)
	public void givenAnAlreadyExistingExceptionWhenConfirmEmailThenThrowAnImpossibleToConfirmEmailAddressException()
			throws ImpossibleToConfirmEmailAddressException {

		doThrow(AlreadyConfirmedEmailAddressException.class).when(emailConfirmer)
				.confirmEmailAddress(AN_INVALID_CONFIRMATION_CODE);
		userService.confirmEmailAddress(AN_INVALID_CONFIRMATION_CODE);
	}

}
