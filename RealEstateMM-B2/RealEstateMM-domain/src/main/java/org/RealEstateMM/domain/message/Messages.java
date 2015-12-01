package org.RealEstateMM.domain.message;

import java.util.List;

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

	public List<Message> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

}
