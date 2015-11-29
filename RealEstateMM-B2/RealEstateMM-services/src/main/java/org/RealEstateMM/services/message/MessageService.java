package org.RealEstateMM.services.message;

import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.message.UserIsNotASellerException;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.message.dtos.MessageDTO;

public class MessageService {

	private Messages messages;

	public MessageService() {
		messages = ServiceLocator.getInstance().getService(Messages.class);
	}

	public void contactSeller(String buyerPseudonym, MessageDTO message)
			throws UserNotFoundException, UserIsNotASellerException {
		messages.contactSeller(buyerPseudonym, message.getRecipient(), message.getMessage());
	}

}
