package org.RealEstateMM.domain.emailsender;

import org.RealEstateMM.domain.emailsender.email.EmailMessage;

public interface EmailSender {

	public void sendEmail(EmailMessage email);

}
