package org.RealEstateMM.services.mail;

public interface MailSender {
	
	public void sendMailConfirmation(String recipientEmail, String confirmationLink) throws CouldNotSendMailException;

}
