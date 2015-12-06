package org.RealEstateMM.persistence.xml.message;

import org.RealEstateMM.domain.message.Message;

public class XmlMessageAssembler {

	public XmlMessage fromMessage(Message message) {
		XmlMessage newMessage = new XmlMessage();
		newMessage.setMessageContent(message.getMessage());
		newMessage.setIsUnread(message.isUnread());
		newMessage.setRecipientPseudo(message.getRecipientPseudonym());
		newMessage.setSenderPseudo(message.getSenderPseudonym());
		return newMessage;

	}

	public Message toMessage(XmlMessage xmlMessage) {
		Message message = new Message(xmlMessage.getSenderPseudo(), xmlMessage.getRecipientPseudo(),
				xmlMessage.getMessageContent(), xmlMessage.getIsUnread());
		return message;
	}
}
