package org.RealEstateMM.domain.message;

import java.util.List;

public interface MessageRepository {

	void add(Message message);

	List<Message> getMessagesByRecipient(String aSellerPseudo);

	Message getMessageById(String messageId);

}
