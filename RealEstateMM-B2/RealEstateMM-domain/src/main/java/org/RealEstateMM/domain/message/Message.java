package org.RealEstateMM.domain.message;

public class Message {

	private String senderPseudonym;
	private String recipientPseudonym;
	private String message;
	private boolean isUnread;

	public Message(String senderPseudonym, String recipientPseudonym, String message, boolean isUnread) {
		this.senderPseudonym = senderPseudonym;
		this.recipientPseudonym = recipientPseudonym;
		this.message = message;
		this.isUnread = isUnread;
	}

	public boolean isUnread() {
		return isUnread;
	}

	public void markAsRead() {
		this.isUnread = false;
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
