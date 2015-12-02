package org.RealEstateMM.persistence.xml.message;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;

public class XmlMessageRepository implements MessageRepository {

	private XmlMarshaller marshaller;
	private XmlMessageAssembler messageAssembler;
	private XmlMessageCollection messageCache;

	public XmlMessageRepository(XmlMarshaller marshaller, XmlMessageAssembler messageAssembler) {
		this.marshaller = marshaller;
		this.messageAssembler = messageAssembler;

		loadMessages();
	}

	@Override
	public void add(Message message) {
		messageCache.add(messageAssembler.fromMessage(message));
	}

	@Override
	public List<Message> getMessagesByRecipient(String aSellerPseudo) {
		List<XmlMessage> xmlMessages = messageCache.findUserMessages(aSellerPseudo);
		return xmlMessages.stream().map(x -> messageAssembler.toMessage(x)).collect(Collectors.toList());
	}

	@Override
	public Message getMessageById(String messageId) {
		// TODO Auto-generated method stub
		return null;
	}

	private void loadMessages() {
		try {
			messageCache = marshaller.unmarshal(XmlMessageCollection.class);
		} catch (EmptyXmlFileException | XmlMarshallingException ex) {
			messageCache = new XmlMessageCollection();
		}
	}

}
