package org.RealEstateMM.services.mail;

import org.RealEstateMM.services.mail.email.Email;

public interface MailSender {

	public void sendEmail(Email email);

}
