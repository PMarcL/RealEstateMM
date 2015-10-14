package org.RealEstateMM.emailsender.email;

public abstract class EmailMessage {

	public final String recipientEmailAddress;
	public final String subject;
	public final String body;

	public EmailMessage(String recipientEmailAddress, String subject, String body) {
		this.recipientEmailAddress = recipientEmailAddress;
		this.subject = subject;
		this.body = body;
	}

}
