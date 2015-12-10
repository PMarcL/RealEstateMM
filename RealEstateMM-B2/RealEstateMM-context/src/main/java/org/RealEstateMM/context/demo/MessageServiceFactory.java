package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.message.MessageFactory;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.message.dtos.MessageAssembler;

public class MessageServiceFactory {

	public MessageService create(MessageRepository messageRepository, UserRepository userRepository) {
		Messages messages = new Messages(messageRepository, userRepository, new MessageFactory());
		return new MessageService(new MessageAssembler(), messages);
	}

}
