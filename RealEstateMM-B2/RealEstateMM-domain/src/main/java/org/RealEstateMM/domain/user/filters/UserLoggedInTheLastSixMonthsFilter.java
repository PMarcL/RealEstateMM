package org.RealEstateMM.domain.user.filters;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.user.User;

public class UserLoggedInTheLastSixMonthsFilter {

	public Collection<User> filter(Collection<User> users) {
		Calendar calendarSixMonthsAgo = Calendar.getInstance();
		calendarSixMonthsAgo.add(Calendar.MONTH, -6);
		Date sixMonthsAgo = calendarSixMonthsAgo.getTime();
		return users.stream().filter(u -> u.hasLoggedLastAfter(sixMonthsAgo)).collect(Collectors.toList());
	}

}
