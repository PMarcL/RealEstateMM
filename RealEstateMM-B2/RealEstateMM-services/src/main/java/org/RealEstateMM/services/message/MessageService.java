package org.RealEstateMM.services.message;

import java.util.LinkedList;
import java.util.List;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.servicelocator.ServiceLocator;
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
		messages.contactSeller(buyerPseudonym, message.getRecipient(), message.getMessage());
	}

	public List<MessageDTO> getMessages(String pseudonym) {
		List<Message> messageList = messages.getMessages();

		List<MessageDTO> messageDTOList = new LinkedList<MessageDTO>();
		for (Message m : messageList) {
			messageDTOList.add(messageAssembler.toDTO(m));
		}

		return messageDTOList;
	}

	// TODO supprimer alert (message read)

}
