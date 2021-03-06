package org.RealEstateMM.domain.message;

import java.util.List;

public interface MessageRepository {

	void add(Message message);

	List<Message> getMessagesByRecipient(String aSellerPseudo);

	void readMessages(String pseudo);

}
