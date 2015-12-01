package org.RealEstateMM.domain.message;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole;
import org.junit.Before;
import org.junit.Test;

public class MessagesTest {

	private static final String A_BUYER_PSEUDO = "AnAwesomeLegend...waitForIt...DaryPseudo";
	private static final String A_SELLER_PSEUDO = "anotherPseudo";
	private static final String AN_INVALID_PSEUDO = "";

	private static final User A_BUYER = mock(User.class);
	private static final User A_SELLER = mock(User.class);
	private static final String A_MESSAGE_ID = UUID.randomUUID().toString();

	private MessageRepository messageRepository;
	private UserRepository userRepository;
	private MessageFactory messageFactory;

	private Messages messages;

	@Before
	public void setUp() throws Exception {
		messageRepository = mock(MessageRepository.class);
		userRepository = mock(UserRepository.class);
		messageFactory = mock(MessageFactory.class);

		given(A_SELLER.getRoleDescription()).willReturn(UserRole.AccessLevel.SELLER);
		given(A_BUYER.getRoleDescription()).willReturn(UserRole.AccessLevel.BUYER);
		given(userRepository.getUserWithPseudonym(A_BUYER_PSEUDO)).willReturn(A_BUYER);
		given(userRepository.getUserWithPseudonym(A_SELLER_PSEUDO)).willReturn(A_SELLER);
		given(userRepository.getUserWithPseudonym(AN_INVALID_PSEUDO))
				.willThrow(new UserNotFoundException(AN_INVALID_PSEUDO));

		messages = new Messages(messageRepository, userRepository, messageFactory);
	}

	@Test
	public void givenBuyerAndSellerPseudonymAndAMessageOrNotWhenContactSellerThenAddProperMessageToMessageRepo()
			throws UserNotFoundException, UserIsNotASellerException {
		String aMessageOrNot = null;

		Message contactSellerMessage = mock(Message.class);
		given(messageFactory.createContactSellerMessage(A_BUYER, A_SELLER, aMessageOrNot))
				.willReturn(contactSellerMessage);

		messages.contactSeller(A_BUYER_PSEUDO, A_SELLER_PSEUDO, aMessageOrNot);

		verify(messageRepository, times(1)).add(contactSellerMessage);
	}

	@Test(expected = UserNotFoundException.class)
	public void givenAnInvalidSellerPseudoWhenContactSellerThenDoNotAddMessageToMessageRepo()
			throws UserNotFoundException, UserIsNotASellerException {
		String aMessageOrNot = null;
		messages.contactSeller(A_BUYER_PSEUDO, AN_INVALID_PSEUDO, aMessageOrNot);
		verify(messageRepository, times(0)).add(any());
	}

	@Test(expected = UserIsNotASellerException.class)
	public void givenAValidPseudoThatIsNotASellerPseudoPseudoWhenContactSellerThenDoNotAddMessageToMessageRepo()
			throws UserNotFoundException, UserIsNotASellerException {
		String aMessageOrNot = null;
		messages.contactSeller(A_BUYER_PSEUDO, A_BUYER_PSEUDO, aMessageOrNot);
		verify(messageRepository, times(0)).add(any());
	}

	@Test
	public void givenAUserPseudoWhenGetNewMessagesThenReturnsAListOfUnreadMessagesSentToTheUser() {
		Message message1 = aMessageMockWithUnreadStatus(true);
		Message message2 = aMessageMockWithUnreadStatus(false);

		List<Message> userMessages = new LinkedList<Message>();
		userMessages.add(message1);
		userMessages.add(message2);
		int numberOfUnreadMessages = 1;

		given(messageRepository.getMessagesByRecipient(A_SELLER_PSEUDO)).willReturn(userMessages);

		List<Message> actual = messages.getUnreadMessages(A_SELLER_PSEUDO);

		assertTrue(actual.contains(message1));
		assertEquals(numberOfUnreadMessages, actual.size());
	}

	@Test(expected = UserIsNotTheRecipient.class)
	public void givenAnotherPseudoThanRecipientWhenReadMessageThenThrowUserIsNotTheRecipientException()
			throws UserIsNotTheRecipient {
		Message aMessage = aMessageMockWithUnreadStatus(true);

		given(aMessage.getRecipientPseudonym()).willReturn(A_SELLER_PSEUDO);
		given(messageRepository.getMessageById(A_MESSAGE_ID)).willReturn(aMessage);

		messages.readMessage(A_MESSAGE_ID, A_BUYER_PSEUDO);
	}

	@Test
	public void givenAnUnreadMessageIdWhenReadMessageThenSetMessageUnreadStatusToFalse() throws UserIsNotTheRecipient {
		Message aMessage = aMessageMockWithUnreadStatus(true);

		given(aMessage.getRecipientPseudonym()).willReturn(A_SELLER_PSEUDO);
		given(messageRepository.getMessageById(A_MESSAGE_ID)).willReturn(aMessage);

		messages.readMessage(A_MESSAGE_ID, A_SELLER_PSEUDO);

		verify(aMessage).markAsRead();
	}

	private Message aMessageMockWithUnreadStatus(boolean isUnread) {
		Message message = mock(Message.class);
		given(message.isUnread()).willReturn(isUnread);
		return message;
	}

}
