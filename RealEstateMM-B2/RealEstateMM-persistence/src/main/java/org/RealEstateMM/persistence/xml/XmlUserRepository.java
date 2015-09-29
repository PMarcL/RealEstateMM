package org.RealEstateMM.persistence.xml;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;

public class XmlUserRepository extends UserRepository {

	XmlUserCollection usersCache;
	XmlMarshaller marshaller;
	XmlUserAssembler userAssembler;

	public XmlUserRepository(XmlMarshaller marshaller, XmlUserAssembler userAssembler) {
		this.marshaller = marshaller;
		this.userAssembler = userAssembler;
		this.usersCache = this.marshaller.unmarshall();
	}

	@Override
	public Optional<User> getUserWithPseudonym(String pseudonym) {
		if (isUserAbsentFromCache(pseudonym))
			return Optional.empty();

		XmlUser xmlUser = usersCache.getUser(pseudonym);
		User user = userAssembler.toUser(xmlUser);
		return Optional.of(user);
	}

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
		marshaller.marshall(usersCache);
	}

}
