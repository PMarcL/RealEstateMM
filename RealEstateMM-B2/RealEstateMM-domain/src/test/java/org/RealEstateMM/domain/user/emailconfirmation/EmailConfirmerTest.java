package org.RealEstateMM.domain.user.emailconfirmation;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.emailsender.EmailSender;
import org.RealEstateMM.emailsender.email.EmailAddressConfirmationEmail;
import org.RealEstateMM.emailsender.email.EmailFactory;
import org.RealEstateMM.encoder.Encoder;
import org.junit.Before;
import org.junit.Test;

public class EmailConfirmerTest {

	private static final User A_USER = mock(User.class);
	private static final String A_PSEUDO = "Josh120";
	private static final String AN_EMAIL_ADDRESS = "someEmailAddress@machin.com";
	private static final String A_SECRET = A_PSEUDO + EmailConfirmer.SEPARATOR + AN_EMAIL_ADDRESS;
	private static final String A_VALID_CONFIRMATION_CODE = "aConfirmationCode";
	private static final String A_CODE_WITH_UNEXISTING_USER = "someInvalidConfirmationCode";

	private UserRepository userRepository;
	private EmailSender mailSender;
	private Encoder encoder;
	private EmailFactory emailFactory;

	private EmailConfirmer emailAddressConfirmer;

	@Before
	public void setUp() throws Exception {
		userRepository = mock(UserRepository.class);
		mailSender = mock(EmailSender.class);
		encoder = mock(Encoder.class);
		emailFactory = mock(EmailFactory.class);

		given(A_USER.getEmailAddress()).willReturn(AN_EMAIL_ADDRESS);
		given(A_USER.getPseudonym()).willReturn(A_PSEUDO);

		emailAddressConfirmer = new EmailConfirmer(userRepository, mailSender, encoder, emailFactory);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationThenSendTheConfirmationMail() {
		EmailAddressConfirmationEmail email = new EmailAddressConfirmationEmail(AN_EMAIL_ADDRESS,
				A_VALID_CONFIRMATION_CODE, null);
		given(encoder.encode(A_SECRET)).willReturn(A_VALID_CONFIRMATION_CODE);
		given(emailFactory.createEmailAddressConfirmationEmail(AN_EMAIL_ADDRESS, A_VALID_CONFIRMATION_CODE))
				.willReturn(email);

		emailAddressConfirmer.sendEmailConfirmation(A_USER);

		verify(mailSender, times(1)).sendEmail(email);
	}

	@Test
	public void givenAValidConfirmationCodeWhenConfirmEmailAddressThenUnlockTheRightUser() {
		given(encoder.decode(A_VALID_CONFIRMATION_CODE)).willReturn(A_SECRET);
		given(userRepository.getUserWithPseudonym(A_PSEUDO)).willReturn(Optional.of(A_USER));

		emailAddressConfirmer.confirmEmailAddress(A_VALID_CONFIRMATION_CODE);

		verify(A_USER, times(1)).unlock(AN_EMAIL_ADDRESS);
	}

	@Test(expected = UserAssociatedToConfirmationCodeDoesNotExistException.class)
	public void givenACodeAssociatedToUnexistingUserWhenConfirmEmailAddressThenThrowInvalidException() {
		given(encoder.decode(A_CODE_WITH_UNEXISTING_USER)).willReturn(A_SECRET);
		given(userRepository.getUserWithPseudonym(A_PSEUDO)).willReturn(Optional.empty());

		emailAddressConfirmer.confirmEmailAddress(A_CODE_WITH_UNEXISTING_USER);
	}

}
