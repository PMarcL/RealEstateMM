package org.RealEstateMM.services.mail;

import java.util.UUID;

public interface MailConfirmationSender {

	public void send(String recipientEmail, UUID confirmationCode) throws CouldNotSendMailException;

}
