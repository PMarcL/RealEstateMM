package org.RealEstateMM.services.user.mailconfirmation;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.services.mail.MailSender;
import org.RealEstateMM.services.mail.email.EmailAddressConfirmationEmail;
import org.RealEstateMM.services.mail.email.EmailFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class EmailConfirmationServiceTest {

	private final String A_VALID_CONFIRMATION_CODE = "aConfirmationCode";

	private MailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;
	private EmailFactory emailFactory;

	private EmailConfirmationService emailConfirmationService;

	@Before
	public void setUp() throws Exception {
		mailSender = mock(MailSender.class);
		emailConfirmationEncoder = mock(EmailConfirmationEncoder.class);
		emailFactory = mock(EmailFactory.class);

		emailConfirmationService = new EmailConfirmationService(mailSender, emailConfirmationEncoder, emailFactory);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationThenSendTheConfirmationMail() {
		User user = mock(User.class);
		String anEmailAddress = "someEmailAddress@machin.com";
		EmailAddressConfirmationEmail expectedEmail = new EmailAddressConfirmationEmail(anEmailAddress,
				A_VALID_CONFIRMATION_CODE);

		given(user.getEmailAddress()).willReturn(anEmailAddress);
		given(emailConfirmationEncoder.getConfirmationCode(user)).willReturn(A_VALID_CONFIRMATION_CODE);
		given(emailFactory.createEmailAddressConfirmationEmail(anEmailAddress, A_VALID_CONFIRMATION_CODE))
				.willReturn(expectedEmail);

		emailConfirmationService.sendEmailConfirmation(user);

		ArgumentCaptor<EmailAddressConfirmationEmail> captor = ArgumentCaptor
				.forClass(EmailAddressConfirmationEmail.class);
		verify(mailSender, times(1)).sendEmail(captor.capture());
		EmailAddressConfirmationEmail actual = captor.getValue();

		assertEquals(expectedEmail, actual);
	}

	@Test
	public void givenAValidConfirmationCodeWhenGetThePseudonymOutOfItThenReturnTheEncodedPseudonym() {
		String aPseudo = "aPseudo";
		given(emailConfirmationEncoder.extractPseudonymFromConfirmationCode(A_VALID_CONFIRMATION_CODE)).willReturn(aPseudo);
		String actual = emailConfirmationService.getConfirmingUserPseudonym(A_VALID_CONFIRMATION_CODE);
		assertEquals(aPseudo, actual);
	}

}
