package org.RealEstateMM.services.message;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.LinkedList;
import java.util.List;

import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.message.dtos.MessageAssembler;
import org.RealEstateMM.services.message.dtos.MessageDTO;
import org.junit.Before;
import org.junit.Test;

public class MessageServiceTest {

	private static final String A_PSEUDONYM = "APseudo";
	private Messages messages;
	private MessageAssembler messageAssembler;
	private MessageService messageService;

	@Before
	public void setUp() throws Exception {
		messages = mock(Messages.class);
		messageAssembler = mock(MessageAssembler.class);

		ServiceLocator.getInstance().registerService(Messages.class, messages);

		messageService = new MessageService(messageAssembler);
	}

	@Test
	public void givenAPseudonymWhenGetMessagesReturnListOfMessageDTO() {
		Message message1 = mock(Message.class);
		Message message2 = mock(Message.class);
		MessageDTO messageDTO1 = mock(MessageDTO.class);
		MessageDTO messageDTO2 = mock(MessageDTO.class);

		List<Message> aMessageList = new LinkedList<Message>();
		aMessageList.add(message1);
		aMessageList.add(message2);

		given(messages.getUserMessages(A_PSEUDONYM)).willReturn(aMessageList);
		given(messageAssembler.toDTO(message1)).willReturn(messageDTO1);
		given(messageAssembler.toDTO(message2)).willReturn(messageDTO2);

		List<MessageDTO> actual = messageService.getUserMessages(A_PSEUDONYM);

		assertTrue(actual.contains(messageDTO1));
		assertTrue(actual.contains(messageDTO2));
	}

}
