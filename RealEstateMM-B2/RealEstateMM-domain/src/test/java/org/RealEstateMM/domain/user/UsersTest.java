package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.emailsender.EmailException;
import org.RealEstateMM.domain.user.emailconfirmation.InvalidEmailConfirmationCodeException;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class UsersTest {
	private final String PSEUDONYM = "bobby134";
	private final String PASSWORD = "myOriginalPassword12345";
	private final String EMAIL_ADDRESS = "bobby@gmail.com";
	private final String CONFIRMATION_CODE = "please confirm my email address";
	private final UserInformations USER_INFORMATIONS = new UserInformations(PSEUDONYM, PASSWORD, null, null,
			EMAIL_ADDRESS, null);

	private User user;
	private UserRepository userRepository;
	private UserEmailAddressValidator emailValidator;
	private Users users;

	@Before
	public void setup() throws Throwable {
		user = mock(User.class);
		given(user.getUserInformations()).willReturn(USER_INFORMATIONS);
		userRepository = mock(UserRepository.class);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(user);
		emailValidator = mock(UserEmailAddressValidator.class);

		users = new Users(userRepository, emailValidator);
	}

	@Test
	public void givenAUserWhenAddUserThenShouldPersistToRespository() throws Throwable {
		users.addUser(user);
		verify(userRepository).addUser(user);
	}

	@Test
	public void givenAUserWhenAddUserThenShouldConfirmEmailAddress() throws Throwable {
		users.addUser(user);
		verify(emailValidator).sendEmailConfirmationMessage(USER_INFORMATIONS);
	}

	@Test(expected = EmailAddressConfirmationException.class)
	public void givenEmailConfirmationFailWhenAddUserThenShouldThrowException() throws Throwable {
		doThrow(new EmailException(new RuntimeException())).when(emailValidator).sendEmailConfirmationMessage(any());
		users.addUser(user);
	}

	@Test
	public void givenPseudonymAndPasswordWhenAuthenticateShouldAuthenticateUserWithSamePseudonym() throws Throwable {
		users.authenticate(PSEUDONYM, PASSWORD);
		verify(user).authenticate(PASSWORD);
	}

	@Test
	public void givenPseudonymAndPasswordWhenAuthenticateShouldReturnAuthenticatedUser() throws Throwable {
		User returnedUser = users.authenticate(PSEUDONYM, PASSWORD);
		assertSame(user, returnedUser);
	}

	@Test(expected = AuthenticationFailedException.class)
	public void givenUserDoesNotExistWhenAuthenticateShouldThrowException() throws Throwable {
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willThrow(new UserNotFoundException(PSEUDONYM));
		users.authenticate(PSEUDONYM, PASSWORD);
	}

	@Test(expected = AuthenticationFailedException.class)
	public void givenUserEmailNotConfirmedWhenAuthenticateShouldThrowException() throws Throwable {
		doThrow(new UnconfirmedEmailException()).when(user).authenticate(PASSWORD);
		users.authenticate(PSEUDONYM, PASSWORD);
	}

	@Test(expected = AuthenticationFailedException.class)
	public void givenInvalidPasswordWhenAuthenticateShouldThrowException() throws Throwable {
		doThrow(new InvalidPasswordException()).when(user).authenticate(PASSWORD);
		users.authenticate(PSEUDONYM, PASSWORD);
	}

	@Test
	public void givenAConfirmationCodeWhenConfirmEmailAddressShouldAskEmailValidatorToValidateUserEmailAddress()
			throws Throwable {
		users.confirmEmailAddress(CONFIRMATION_CODE);
		verify(emailValidator).confirmEmailAddress(CONFIRMATION_CODE, userRepository);
	}

	@Test(expected = EmailAddressConfirmationException.class)
	public void givenAnInvalidConfirmationCodeWhenConfirmEmailAddressShouldThrowException() throws Throwable {
		doThrow(new InvalidEmailConfirmationCodeException()).when(emailValidator).confirmEmailAddress(CONFIRMATION_CODE,
				userRepository);
		users.confirmEmailAddress(CONFIRMATION_CODE);
	}

	@Test
	public void givenPseudonymWhenGetUserThenShouldReturnUserWithCorrespondingPseudonym() throws Throwable {
		User returnedUser = users.getUser(PSEUDONYM);
		assertSame(user, returnedUser);
	}

	@Test
	public void givenAnExistingUserWhenEditUserProfileShouldPersistUserAfterUpdatingUserInformations()
			throws Throwable {
		users.updateUserProfile(USER_INFORMATIONS);

		InOrder inOrder = inOrder(user, userRepository);
		inOrder.verify(user).updateUserInformations(USER_INFORMATIONS);
		inOrder.verify(userRepository).replaceUser(user);
	}

	@Test
	public void givenNewEmailAddressDifferentFromUserEmailAddressWhenUpdateUserProfileShouldSendEmailAddressConfirmationEmail()
			throws Throwable {
		given(user.hasEmailAddress(EMAIL_ADDRESS)).willReturn(false);
		users.updateUserProfile(USER_INFORMATIONS);
		verify(emailValidator).sendEmailConfirmationMessage(USER_INFORMATIONS);
	}

	@Test(expected = EmailAddressConfirmationException.class)
	public void givenEmailAddressConfirmationFailWhenUpdateUserProfileShouldThrowException() throws Throwable {
		doThrow(new EmailException(new RuntimeException())).when(emailValidator).sendEmailConfirmationMessage(any());
		users.updateUserProfile(USER_INFORMATIONS);
	}

	@Test
	public void givenNewEmailAddressDifferentFromEmailAddressWhenUpdateUserProfileShouldLockUserBeforeReplacingItInRepository()
			throws Throwable {
		given(user.hasEmailAddress(EMAIL_ADDRESS)).willReturn(false);

		users.updateUserProfile(USER_INFORMATIONS);

		InOrder inOrder = inOrder(user, userRepository);
		inOrder.verify(user).lock();
		inOrder.verify(userRepository).replaceUser(user);
	}

	@Test
	public void givenEmailAddressIsSameAsCurrentEmailAddressWhenUdpateUserProfileShouldNotSendEmailAddressConfirmationEmail()
			throws Throwable {
		given(user.hasEmailAddress(EMAIL_ADDRESS)).willReturn(true);
		users.updateUserProfile(USER_INFORMATIONS);
		verify(emailValidator, never()).sendEmailConfirmationMessage(any(UserInformations.class));
	}
}
