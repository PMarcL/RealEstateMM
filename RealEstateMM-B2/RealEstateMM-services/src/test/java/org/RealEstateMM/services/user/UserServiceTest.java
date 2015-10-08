package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.services.dtos.user.UserAssembler;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.UserDTOBuilder;
import org.RealEstateMM.services.mail.MailConfirmationSender;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private final UserDTO A_USER_DTO = new UserDTOBuilder().build();
	private final String A_PSEUDO = "pseudo34";
	private final String A_PASSWORD = "pw1234";
	private final String INVALID_PASSWORD = "posdf33";
	private final String AN_EMAIL = "anEmail@machin.com";
	private final UUID EMAIL_CONFIRMATION_CODE = UUID.randomUUID();
	private final User A_USER = mock(User.class);

	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private MailConfirmationSender mailConfirmationSender;

	private UserService userService;

	@Before
	public void setup() throws Exception {
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);
		mailConfirmationSender = mock(MailConfirmationSender.class);

		given(userAssembler.fromDTO(A_USER_DTO)).willReturn(A_USER);

		userService = new UserService(userRepository, userAssembler, mailConfirmationSender);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		setUpUserForEmailConfirmation();
		userService.create(A_USER_DTO);
		verify(userRepository).addUser(A_USER);
	}

	@Test
	public void whenCreateUserThenSendEmailConfirmationWithCreatedUserEmailConfirmationCode() {
		setUpUserForEmailConfirmation();
		userService.create(A_USER_DTO);
		verify(mailConfirmationSender, times(1)).send(AN_EMAIL, EMAIL_CONFIRMATION_CODE);
	}

	private void setUpUserForEmailConfirmation() {
		UserInformations someUserInfo = new UserInformations(A_PSEUDO, null, null, null, AN_EMAIL, null);

		given(A_USER.getUserInformations()).willReturn(someUserInfo);
		given(A_USER.getEmailConfirmationCode()).willReturn(EMAIL_CONFIRMATION_CODE);
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

}
