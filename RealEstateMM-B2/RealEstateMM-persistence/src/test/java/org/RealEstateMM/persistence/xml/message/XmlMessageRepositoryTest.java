package org.RealEstateMM.persistence.xml.message;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class XmlMessageRepositoryTest {
	
	private XmlMarshaller marshaller;
	private XmlMessageAssembler assembler;
	private MessageRepository repository;
	
	private static final String RECIPIENT = "MarshallErikson";

	@Before
	public void setup(){
		marshaller = Mockito.mock(XmlMarshaller.class);
		assembler = Mockito.mock(XmlMessageAssembler.class);
	}

	@Test
	public void givenAnEmptyMessageCacheGetMessageByRecipientShouldReturnEmptyList(){
		given(marshaller.unmarshal(XmlMessageCollection.class)).willThrow(new EmptyXmlFileException());
		repository = new XmlMessageRepository(marshaller, assembler);
		
		List<Message> messages = repository.getMessagesByRecipient(RECIPIENT);
		
		assertTrue(messages.size() == 0);
	}
	
	

}
