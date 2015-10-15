package org.RealEstateMM.services.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
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

	private final String INVALID_CONFIRMATION_CODE = "anInvalidCode";
	private final UserDTO USER_DTO = new UserDTOBuilder().build();
	private final String PSEUDONYM = "pseudo34";
	private final String PASSWORD = "pw1234";
	private final String INVALID_PASSWORD = "posdf33";

	private User user;
	private UserRepository userRepository;
	private UserAssembler userAssembler;
	private UserEmailAddressValidator emailConfirmer;

	private UserService userService;

	@Before
	public void setup() {
		user = mock(User.class);
		userRepository = mock(UserRepository.class);
		userAssembler = mock(UserAssembler.class);
		emailConfirmer = mock(UserEmailAddressValidator.class);

		given(userAssembler.fromDTO(USER_DTO)).willReturn(user);

		userService = new UserService(userRepository, userAssembler, emailConfirmer);
	}

	@Test
	public void whenCreateUserThenAddNewUserToRepository() {
		userService.createUser(USER_DTO);
		verify(userRepository).addUser(user);
	}

	@Test
	public void whenCreateUserThenSendEmailConfirmationWithCreatedUserEmailConfirmationCode() {
		UserInformations userInfos = mock(UserInformations.class);
		given(user.getUserInformations()).willReturn(userInfos);

		userService.createUser(USER_DTO);

		verify(emailConfirmer).sendEmailConfirmationMessage(userInfos);
	}

	@Test
	public void givenAPseudonymWithRightPassWordWhenAuthenticateThenReturnTheUserDTO() throws Exception {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.of(user));
		given(user.hasPassword(PASSWORD)).willReturn(true);
		given(userAssembler.toDTO(user)).willReturn(USER_DTO);

		UserDTO actual = userService.authenticate(PSEUDONYM, PASSWORD);

		assertEquals(USER_DTO, actual);
	}

	@Test(expected = UserDoesNotExistException.class)
	public void givenNoUserWhenAuthenticateThenThrowUserNotFoundException() throws Exception {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.empty());
		userService.authenticate(PSEUDONYM, PASSWORD);
	}

	@Test(expected = InvalidPasswordException.class)
	public void givenAnInvalidPasswordWhenAuthenticateThrowInvalidPasswordException() throws Exception {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(Optional.of(user));
		given(user.hasPassword(PASSWORD)).willReturn(true);

		userService.authenticate(PSEUDONYM, INVALID_PASSWORD);
	}

	@Test(expected = ImpossibleToConfirmEmailAddressException.class)
	public void givenAnInvalidConfirmationCodeWhenConfirmEmailThenThrowAnImpossibleToConfirmEmailAddressException()
			throws Exception {

		confirmationCodeIsInvalid();
		userService.confirmEmailAddress(INVALID_CONFIRMATION_CODE);
	}

	private void confirmationCodeIsInvalid() {
		doThrow(InvalidEmailConfirmationCodeException.class).when(emailConfirmer).confirmEmailAddress(anyString(),
				any(UserRepository.class));
	}

}