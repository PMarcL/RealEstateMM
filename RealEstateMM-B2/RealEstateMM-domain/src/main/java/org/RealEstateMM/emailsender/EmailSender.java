package org.RealEstateMM.emailsender;

import org.RealEstateMM.emailsender.email.EmailMessage;

public interface EmailSender {

	public void sendEmail(EmailMessage email);

}
