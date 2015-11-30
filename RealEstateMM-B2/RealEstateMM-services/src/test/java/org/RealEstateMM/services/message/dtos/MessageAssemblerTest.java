package org.RealEstateMM.services.message.dtos;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.message.Message;
import org.junit.Before;
import org.junit.Test;

public class MessageAssemblerTest {

	private static String A_MESSAGE_TEXT = "allo";
	private static String A_SENDER = "john";
	private static String A_RECIPIENT = "jean";
	private static boolean IS_UNREAD = true;
	private static final Message A_MESSAGE = new Message(A_MESSAGE_TEXT, A_SENDER, A_RECIPIENT, IS_UNREAD);

	private MessageAssembler messageAssembler;

	@Before
	public void setUp() throws Exception {
		messageAssembler = new MessageAssembler();
	}

	@Test
	public void givenAMessageWhenToDTOThenReturnsAMessageDTOWithTheCorrectValues() {
		MessageDTO actual = messageAssembler.toDTO(A_MESSAGE);

		assertEquals(A_MESSAGE.getMessage(), actual.getMessage());
		assertEquals(A_MESSAGE.getSenderPseudonym(), actual.getSenderPseudonym());
		assertEquals(A_MESSAGE.getRecipientPseudonym(), actual.getRecipientPseudonym());
		assertEquals(A_MESSAGE.isUnread(), actual.isUnread());
	}

}
