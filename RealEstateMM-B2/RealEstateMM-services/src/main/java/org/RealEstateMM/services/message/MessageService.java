package org.RealEstateMM.services.message;

import java.util.LinkedList;
import java.util.List;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.message.dtos.MessageAssembler;
import org.RealEstateMM.services.message.dtos.MessageDTO;

public class MessageService {

	private Messages messages;
	private MessageAssembler messageAssembler;

	public MessageService(MessageAssembler messageAssembler, Messages messages) {
		this.messageAssembler = messageAssembler;
		this.messages = messages;
	}

	public void contactSeller(String buyerPseudonym, MessageDTO message)
			throws UserNotFoundException, UserIsNotASellerException {
		messages.contactSeller(buyerPseudonym, message.getRecipientPseudonym(), message.getMessage());
	}

	public List<MessageDTO> getUserMessages(String pseudonym) {
		List<Message> messageList = messages.getUserMessages(pseudonym);

		List<MessageDTO> messageDTOList = new LinkedList<MessageDTO>();
		for (Message m : messageList) {
			messageDTOList.add(messageAssembler.toDTO(m));
		}

		return messageDTOList;
	}

	public void readMessages(String pseudo) {
		messages.readMessages(pseudo);
	}

}
