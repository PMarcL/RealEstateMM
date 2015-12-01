package org.RealEstateMM.services.message.dtos;

import org.RealEstateMM.domain.message.Message;

public class MessageAssembler {

	public MessageDTO toDTO(Message message) {
		String messageText = message.getMessage();
		String senderPseudonym = message.getSenderPseudonym();
		String recipientPseudonym = message.getRecipientPseudonym();
		boolean isUnread = message.isUnread();

		return new MessageDTO(messageText, senderPseudonym, recipientPseudonym, isUnread);
	}

}
