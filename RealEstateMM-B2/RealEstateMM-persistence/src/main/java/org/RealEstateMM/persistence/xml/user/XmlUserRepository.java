package org.RealEstateMM.persistence.xml.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;

public class XmlUserRepository extends UserRepository {

	XmlUserCollection usersCache;
	XmlMarshaller marshaller;
	XmlUserAssembler userAssembler;

	public XmlUserRepository(XmlMarshaller marshaller, XmlUserAssembler userAssembler) {
		this.marshaller = marshaller;
		this.userAssembler = userAssembler;

		loadUsers();
	}

	private void loadUsers() {
		try {
			usersCache = marshaller.unmarshal(XmlUserCollection.class);
		} catch (EmptyXmlFileException e) {
			usersCache = new XmlUserCollection();
		} catch (XmlMarshallingException e) {
			usersCache = new XmlUserCollection();
		}
	}

	@Override
	public Optional<User> getUserWithPseudonym(String pseudonym) {
		if (isUserAbsentFromCache(pseudonym))
			return Optional.empty();

		XmlUser xmlUser = usersCache.getUser(pseudonym);
		User user = userAssembler.toUser(xmlUser);
		return Optional.of(user);
	}

	@Override
	public java.util.ArrayList<User> getAll() {
		ArrayList<User> users = new ArrayList<User>();
		List<XmlUser> xmlUsers = usersCache.getUsers();

		for (XmlUser xmlUser : xmlUsers) {
			users.add(userAssembler.toUser(xmlUser));
		}
		return users;
	};

	private boolean isUserAbsentFromCache(String pseudonym) {
		return !usersCache.contains(pseudonym);
	}

	@Override
	protected boolean contains(String pseudonym) {
		return usersCache.contains(pseudonym);
	}

	@Override
	protected void add(User newUser) {
		XmlUser xmlUser = userAssembler.fromUser(newUser);
		usersCache.add(xmlUser);
		marshalUsers();
	}

	private void marshalUsers() {
		marshaller.marshal(XmlUserCollection.class, usersCache);
	}

	@Override
	protected void removeUserWithPseudonym(String pseudonym) {
		usersCache.removeUserWithPseudonym(pseudonym);
		marshalUsers();
	}

}
