package org.RealEstateMM.persistence.xml.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XmlMessageCollectionTest {

	private XmlMessageCollection messageCache;
	
	private static final String A_PSEUDO = "Rand";
	private static final String ANOTHER_PSEUDO=  "Mat";
	
	@Before
	public void setup(){
		messageCache = new XmlMessageCollection();
	}
	
	@Test
	public void givenANewMessageCollectionItShouldBeEmpty(){
		assertTrue(messageCache.getMessages().isEmpty());
	}
	
	@Test
	public void givenAMessageCollectionAddingAMessageShouldNowGiveASingleMessage(){
		messageCache.add(new XmlMessage());
		
		assertEquals(1, messageCache.getMessages().size());
	}
	
	@Test public void givenAnUnreadMessageReadingTheMessageShouldNowMarktheMessageAsRead(){
		XmlMessage message = new XmlMessage();
		message.setRecipientPseudo(A_PSEUDO);
		message.setIsUnread(true);
		messageCache.add(message);
		
		messageCache.readUserMessages(A_PSEUDO);
		
		assertFalse(messageCache.getMessages().get(0).getIsUnread());
	}
	
	@Test public void givenMessagesFromdifferentUsersGetUserMessagesShouldReturnOnlyHisMessage(){
		XmlMessage message = new XmlMessage();
		message.setRecipientPseudo(A_PSEUDO);
		message.setIsUnread(true);
		messageCache.add(message);
		XmlMessage anotherMessage = new XmlMessage();
		anotherMessage.setRecipientPseudo(ANOTHER_PSEUDO);
		anotherMessage.setIsUnread(true);
		messageCache.add(anotherMessage);
		
		List<XmlMessage> messages = messageCache.findUserMessages(A_PSEUDO);
		
		assertEquals(1, messages.size());
		assertEquals(A_PSEUDO, messages.get(0).getRecipientPseudo());
	}
}
