package org.RealEstateMM.domain.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class UserFilter {

	public Collection<User> getUsersWithUserType(Collection<User> users, AccessLevel userType) {
		Stream<User> userOfGivenTypeStream = users.stream().filter(u -> u.getRoleDescription() == userType);
		return userOfGivenTypeStream.collect(Collectors.toList());
	}

	public Collection<User> getUsersLastLoggedInTheLast6Months(Collection<User> users) {
		Calendar calendarSixMonthsAgo = Calendar.getInstance();
		calendarSixMonthsAgo.add(Calendar.MONTH, -6);
		Date sixMonthsAgo = calendarSixMonthsAgo.getTime();
		return users.stream().filter(u -> u.hasLoggedLastAfter(sixMonthsAgo)).collect(Collectors.toList());
	}

}
