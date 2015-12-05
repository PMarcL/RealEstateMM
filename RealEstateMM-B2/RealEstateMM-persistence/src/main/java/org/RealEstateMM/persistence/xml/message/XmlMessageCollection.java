package org.RealEstateMM.persistence.xml.message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "messages")
public class XmlMessageCollection {
	private List<XmlMessage> messages;

	public XmlMessageCollection() {
		messages = new ArrayList<XmlMessage>();
	}

	public List<XmlMessage> findUserMessages(String recipientPseudo) {
		return messages.stream().filter(x -> x.getRecipientPseudo().equals(recipientPseudo))
				.collect(Collectors.toList());
	}

	public void add(XmlMessage newMessage) {
		messages.add(newMessage);
	}
	
	public List<XmlMessage> getMessages() {
		return messages;
	}

	@XmlElement(name = "message")
	public void setMessages(List<XmlMessage> messages) {
		this.messages = messages;
	}
}
