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

public class MailConfirmationServiceTest {

	private MailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;
	private EmailFactory emailFactory;

	private MailConfirmationService mailConfirmationService;

	@Before
	public void setUp() throws Exception {
		mailSender = mock(MailSender.class);
		emailConfirmationEncoder = mock(EmailConfirmationEncoder.class);
		emailFactory = mock(EmailFactory.class);

		mailConfirmationService = new MailConfirmationService(mailSender, emailConfirmationEncoder, emailFactory);
	}

	@Test
	public void givenAUserWhenSendEmailConfirmationThenSendTheConfirmationMail() {
		User user = mock(User.class);
		String anEmailAddress = "someEmailAddress@machin.com";
		String aConfirmationCode = "someConfirmationCode";
		EmailAddressConfirmationEmail expectedEmail = new EmailAddressConfirmationEmail(anEmailAddress,
				aConfirmationCode);

		given(user.getEmailAddress()).willReturn(anEmailAddress);
		given(emailConfirmationEncoder.getConfirmationCode(user)).willReturn(aConfirmationCode);
		given(emailFactory.createEmailAddressConfirmationEmail(anEmailAddress, aConfirmationCode))
				.willReturn(expectedEmail);

		mailConfirmationService.sendEmailConfirmation(user);

		ArgumentCaptor<EmailAddressConfirmationEmail> captor = ArgumentCaptor
				.forClass(EmailAddressConfirmationEmail.class);
		verify(mailSender, times(1)).sendEmail(captor.capture());
		EmailAddressConfirmationEmail actual = captor.getValue();

		assertEquals(expectedEmail, actual);
	}

}
