package org.RealEstateMM.persistence.xml;

import java.util.Optional;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;

public class XmlUserRepository extends UserRepository {

	@Override
	public Optional<User> getUserWithPseudonym(String pseudonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean contains(String pseudonym) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void add(User user) {
		// TODO Auto-generated method stub

	}

}
