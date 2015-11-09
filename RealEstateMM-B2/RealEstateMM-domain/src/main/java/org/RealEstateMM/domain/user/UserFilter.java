package org.RealEstateMM.domain.user;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFilter {

	public Collection<User> getBuyers(Collection<User> users) {
		Stream<User> buyerStream = users.stream().filter(u -> u.getUserTypeDescription() == UserType.BUYER);
		return buyerStream.collect(Collectors.toList());
	}

	public Collection<User> getSellers(Collection<User> users) {
		Stream<User> sellerStream = users.stream().filter(u -> u.getUserTypeDescription() == UserType.SELLER);
		return sellerStream.collect(Collectors.toList());
	}

	public Collection<User> getActiveUsers(Collection<User> users) {
		return users.stream().filter(u -> u.isActive()).collect(Collectors.toList());
	}

}
