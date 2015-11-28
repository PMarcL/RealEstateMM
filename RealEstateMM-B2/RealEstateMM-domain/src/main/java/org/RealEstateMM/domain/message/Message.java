package org.RealEstateMM.domain.message;

public class Message {

	protected String senderPseudonym;
	protected String recipientPseudonym;
	protected String message;

	public Message(String senderPseudonym, String recipientPseudonym, String message) {
		super();
		this.senderPseudonym = senderPseudonym;
		this.recipientPseudonym = recipientPseudonym;
		this.message = message;
	}

	public String getSenderPseudonym() {
		return senderPseudonym;
	}

	public void setSenderPseudonym(String senderPseudonym) {
		this.senderPseudonym = senderPseudonym;
	}

	public String getRecipientPseudonym() {
		return recipientPseudonym;
	}

	public void setRecipientPseudonym(String recipientPseudonym) {
		this.recipientPseudonym = recipientPseudonym;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
