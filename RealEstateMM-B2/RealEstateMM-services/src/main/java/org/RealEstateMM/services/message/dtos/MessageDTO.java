package org.RealEstateMM.services.message.dtos;

public class MessageDTO {

	private String message;
	private String sender;
	private String recipient;
	private boolean isUnread;

	public MessageDTO(String message, String sender, String recipient, boolean isUnread) {
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
		this.isUnread = isUnread;
	}

	public boolean isUnread() {
		return isUnread;
	}

	public void setUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}

	public String getSenderPseudonym() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecipientPseudonym() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
