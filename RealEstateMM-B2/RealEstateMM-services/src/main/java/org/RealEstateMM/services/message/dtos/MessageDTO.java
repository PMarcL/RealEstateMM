package org.RealEstateMM.services.message.dtos;

public class MessageDTO {

	private String message;
	private String recipient;

	public MessageDTO(String message, String recipientPseudonym) {
		super();
		this.message = message;
		this.recipient = recipientPseudonym;
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
