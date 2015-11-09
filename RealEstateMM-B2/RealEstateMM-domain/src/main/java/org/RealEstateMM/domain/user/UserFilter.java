package org.RealEstateMM.domain.user;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFilter {

	public Collection<User> getUsersWithUserType(Collection<User> users, String userType) {
		Stream<User> userOfGivenTypeStream = users.stream().filter(u -> u.getUserTypeDescription() == userType);
		return userOfGivenTypeStream.collect(Collectors.toList());
	}

	public Collection<User> getActiveUsers(Collection<User> users) {
		return users.stream().filter(u -> u.isActive()).collect(Collectors.toList());
	}

}
