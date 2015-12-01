package org.RealEstateMM.services.message;

import java.util.LinkedList;
import java.util.List;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.message.UserIsNotTheRecipient;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.message.dtos.MessageAssembler;
import org.RealEstateMM.services.message.dtos.MessageDTO;

public class MessageService {

	private Messages messages;
	private MessageAssembler messageAssembler;

	public MessageService(MessageAssembler messageAssembler) {
		this.messageAssembler = messageAssembler;
		messages = ServiceLocator.getInstance().getService(Messages.class);
	}

	public void contactSeller(String buyerPseudonym, MessageDTO message)
			throws UserNotFoundException, UserIsNotASellerException {
		messages.contactSeller(buyerPseudonym, message.getRecipientPseudonym(), message.getMessage());
	}

	public List<MessageDTO> getUnreadMessages(String pseudonym) {
		List<Message> messageList = messages.getUnreadMessages(pseudonym);

		List<MessageDTO> messageDTOList = new LinkedList<MessageDTO>();
		for (Message m : messageList) {
			messageDTOList.add(messageAssembler.toDTO(m));
		}

		return messageDTOList;
	}

	public void readMessage(String messageId, String pseudo) throws UserIsNotTheRecipient {
		messages.readMessage(messageId, pseudo);
	}

}
