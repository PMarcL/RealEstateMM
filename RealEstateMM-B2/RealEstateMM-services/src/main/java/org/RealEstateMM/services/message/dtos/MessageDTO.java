package org.RealEstateMM.services.message.dtos;

public class MessageDTO {

	private String message;
	private String sender;
	private String recipient;

	public MessageDTO(String message, String sender, String recipient) {
		super();
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
	}

	public String getSender() {
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

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
