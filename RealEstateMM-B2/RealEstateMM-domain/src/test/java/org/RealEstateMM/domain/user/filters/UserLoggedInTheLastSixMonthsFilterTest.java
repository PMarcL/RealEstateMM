package org.RealEstateMM.domain.user.filters;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.user.User;
import org.junit.Before;
import org.junit.Test;

public class UserLoggedInTheLastSixMonthsFilterTest {

	private UserLoggedInTheLastSixMonthsFilter filter;

	@Before
	public void setUp() {
		filter = new UserLoggedInTheLastSixMonthsFilter();
	}

	@Test
	public void givenAListOfUsersWhenGetLastLoggedInTheLast6MonthsThenReturnsListOfUserLastLoggedInTheLast6Months() {
		User lastLoggedThisWeek = aUserMockLastLoggedInTheLastSixMonths(true);
		User lastLoggedLastYearUser = aUserMockLastLoggedInTheLastSixMonths(false);

		Collection<User> users = new ArrayList<>();
		users.add(lastLoggedLastYearUser);
		users.add(lastLoggedThisWeek);
		int numberOfActiveUser = 1;

		Collection<User> actual = filter.filter(users);

		assertTrue(actual.contains(lastLoggedThisWeek));
		assertEquals(numberOfActiveUser, actual.size());

	}

	private User aUserMockLastLoggedInTheLastSixMonths(boolean hasLoggedInTheLastSixMonths) {
		User user = mock(User.class);
		given(user.hasLoggedLastAfter(any())).willReturn(hasLoggedInTheLastSixMonths);
		return user;
	}

}
