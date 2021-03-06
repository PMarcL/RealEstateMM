package org.RealEstateMM.domain.user.emailconfirmation;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class UserEmailAddressValidatorTest {
	private final String EMAIL_ADDRESS = "bob134@qqc.net";
	private final String PSEUDONYM = "bob134";
	private final UserInformations USER_INFOS = createUserInformations();
	private final String CONFIRMATION_CODE_VALUE = "confirmationCode";

	private ConfirmationCodeFactory confirmCodeFactory;
	private EmailMessageFactory messageFactory;
	private ConfirmationCode confirmationCode;
	private EmailSender emailSender;

	private UserEmailAddressValidator validator;
	private UserRepository userRepository;
	private User user;

	@Before
	public void setup() throws Throwable {
		emailSender = mock(EmailSender.class);
		confirmCodeFactory = mock(ConfirmationCodeFactory.class);
		user = mock(User.class);
		userRepository = mock(UserRepository.class);
		given(userRepository.getUserWithPseudonym(PSEUDONYM)).willReturn(user);
		messageFactory = mock(EmailMessageFactory.class);
		confirmationCode = mock(ConfirmationCode.class);
		given(confirmationCode.getPseudonym()).willReturn(PSEUDONYM);
		given(confirmationCode.getEmailAddress()).willReturn(EMAIL_ADDRESS);
		given(confirmCodeFactory.createConfirmationCode(PSEUDONYM, EMAIL_ADDRESS)).willReturn(confirmationCode);
		given(confirmCodeFactory.createConfirmationCode(CONFIRMATION_CODE_VALUE)).willReturn(confirmationCode);

		validator = new UserEmailAddressValidator(confirmCodeFactory, messageFactory, emailSender);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationMessageShouldCreateConfirmationCode() {
		validator.sendValidationRequest(USER_INFOS);
		verify(confirmCodeFactory).createConfirmationCode(PSEUDONYM, EMAIL_ADDRESS);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationMessageShouldCreateEmailMessage() {
		validator.sendValidationRequest(USER_INFOS);
		verify(messageFactory).createEmailAddressConfirmationMessage(EMAIL_ADDRESS, confirmationCode);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationMessageShouldSendEmailMessage() {
		EmailAddressConfirmationMessage message = mock(EmailAddressConfirmationMessage.class);
		given(messageFactory.createEmailAddressConfirmationMessage(EMAIL_ADDRESS, confirmationCode))
				.willReturn(message);

		validator.sendValidationRequest(USER_INFOS);

		verify(emailSender).sendEmail(message);
	}

	@Test
	public void givenAConfirmationCodeValueWhenConfirmEmailAddressShouldCreateConfirmationCode() {
		validator.confirmUserInformation(CONFIRMATION_CODE_VALUE, userRepository);
		verify(confirmCodeFactory).createConfirmationCode(CONFIRMATION_CODE_VALUE);
	}

	@Test
	public void givenUserExistsAndHasSameEmailAddressAsConfirmationCodeWhenConfirmEmailAddressShouldUnlockUserAndPersistItBackToRepository() {
		given(user.hasEmailAddress(EMAIL_ADDRESS)).willReturn(true);

		validator.confirmUserInformation(CONFIRMATION_CODE_VALUE, userRepository);

		InOrder inOrder = inOrder(user, userRepository);
		inOrder.verify(user).unlock();
		inOrder.verify(userRepository).replaceUser(user);
	}

	@Test
	public void givenUserDoesNotExistWhenConfirmEmailAddressShouldNotUnlockAnyUser() throws Throwable {
		given(userRepository.getUserWithPseudonym(anyString())).willThrow(new UserNotFoundException(PSEUDONYM));
		validator.confirmUserInformation(CONFIRMATION_CODE_VALUE, userRepository);
		verify(user, never()).unlock();
	}

	@Test
	public void givenUserExistsButDoesNotHaveSameEmailAddressWhenConfirmEmailAddressShouldNotUnlockUser() {
		given(user.hasEmailAddress(EMAIL_ADDRESS)).willReturn(false);
		validator.confirmUserInformation(CONFIRMATION_CODE_VALUE, userRepository);
		verify(user, never()).unlock();
	}

	private UserInformations createUserInformations() {
		return new UserInformations(PSEUDONYM, null, null, null, EMAIL_ADDRESS, null);
	}
}
