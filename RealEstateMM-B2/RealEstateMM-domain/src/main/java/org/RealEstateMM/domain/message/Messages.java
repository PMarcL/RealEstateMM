package org.RealEstateMM.domain.message;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole;

public class Messages {

	private MessageRepository messageRepository;
	private UserRepository userRepository;
	private MessageFactory messageFactory;

	public Messages(MessageRepository messageRepository, UserRepository userRepository, MessageFactory messageFactory) {
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
		this.messageFactory = messageFactory;
	}

	public void contactSeller(String buyerPseudonym, String sellerPseudonym, String buyersMessage)
			throws UserNotFoundException, UserIsNotASellerException {
		User buyer = userRepository.getUserWithPseudonym(buyerPseudonym);
		User seller = userRepository.getUserWithPseudonym(sellerPseudonym);

		if (seller.getRoleDescription() != UserRole.AccessLevel.SELLER) {
			throw new UserIsNotASellerException(sellerPseudonym);
		}

		Message message = messageFactory.createContactSellerMessage(buyer, seller, buyersMessage);
		messageRepository.add(message);
	}

	public List<Message> getUnreadMessages(String pseudonym) {
		List<Message> userMessages = messageRepository.getMessagesByRecipient(pseudonym);
		return userMessages.stream().filter(u -> u.isUnread()).collect(Collectors.toList());
	}

	public void readMessage(String messageId, String pseudo) throws UserIsNotTheRecipient {
		Message message = messageRepository.getMessageById(messageId);

		if (!pseudo.equals(message.getRecipientPseudonym())) {
			throw new UserIsNotTheRecipient();
		}

		message.markAsRead();
	}

}
