package org.RealEstateMM.persistence.xml.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.user.XmlUser;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserCollection;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class XmlUserRepositoryTest {
	private final String PSEUDONYM = "bob123";

	private XmlUserRepository repository;
	private XmlMarshaller marshaller;
	private XmlUserCollection userCollection;
	private XmlUserAssembler assembler;
	private XmlUser xmlUser;

	@Before
	public void setup() {
		setupMocks();
		addUserWithPseudonym(PSEUDONYM);

		repository = new XmlUserRepository(marshaller, assembler);
	}

	@Test
	public void unmarshallUserCollectionWhenCreated() {
		assertTrue(repository.contains(PSEUDONYM));
	}

	@Test
	public void givenEmptyXmlFileWhenCreatedShouldInitializeEmptyUserCollection() {
		given(marshaller.unmarshal(XmlUserCollection.class)).willThrow(new EmptyXmlFileException());
		repository = new XmlUserRepository(marshaller, assembler);
		assertFalse(repository.contains(PSEUDONYM));
	}

	@Test
	public void givenNewUserWhenAddedShouldAddToUserCollectionBeforeMarshalling() {
		User newUser = mock(User.class);

		repository.add(newUser);

		InOrder inOrder = inOrder(userCollection, marshaller);
		inOrder.verify(userCollection).add(xmlUser);
		inOrder.verify(marshaller).marshal(XmlUserCollection.class, userCollection);
	}

	@Test
	public void givenExistingUserPseudonymWhenRetrievingItWithPseudonymShouldReturnCorrespondingUser() {
		User assembledUser = mock(User.class);
		given(assembler.toUser(xmlUser)).willReturn(assembledUser);

		Optional<User> returnedUser = repository.getUserWithPseudonym(PSEUDONYM);

		assertSame(assembledUser, returnedUser.get());
	}

	@Test
	public void givenNonExistingUserPseudonymWhenRetrievingItWithPseudonymShouldReturnEmptyResult() {
		final String NON_EXISTING_PSEUDONYM = "ronald25";
		Optional<User> returnedUser = repository.getUserWithPseudonym(NON_EXISTING_PSEUDONYM);
		assertFalse(returnedUser.isPresent());
	}

	@Test
	public void givenExistingUserWhenRemoveWithPseudonymShouldRemoveFromUserCacheBeforeMarshalling() {
		addUserWithPseudonym(PSEUDONYM);

		repository.removeUserWithPseudonym(PSEUDONYM);

		InOrder inOrder = inOrder(userCollection, marshaller);
		inOrder.verify(userCollection).removeUserWithPseudonym(PSEUDONYM);
		inOrder.verify(marshaller).marshal(XmlUserCollection.class, userCollection);
	}

	private void addUserWithPseudonym(String pseudonym) {
		given(userCollection.contains(pseudonym)).willReturn(true);
		given(userCollection.getUser(pseudonym)).willReturn(xmlUser);
	}

	private void setupMocks() {
		xmlUser = mock(XmlUser.class);
		assembler = mock(XmlUserAssembler.class);
		given(assembler.fromUser(any(User.class))).willReturn(xmlUser);
		userCollection = mock(XmlUserCollection.class);
		marshaller = mock(XmlMarshaller.class);
		given(marshaller.unmarshal(XmlUserCollection.class)).willReturn(userCollection);
	}
}
