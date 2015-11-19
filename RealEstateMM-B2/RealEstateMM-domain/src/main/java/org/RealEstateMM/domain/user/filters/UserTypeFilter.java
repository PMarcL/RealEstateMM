package org.RealEstateMM.domain.user.filters;

import java.util.Collection;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserTypeFilter {

	public Collection<User> filter(Collection<User> users, AccessLevel userType) {
		return users.stream().filter(u -> u.getRoleDescription() == userType).collect(Collectors.toList());
	}

}
