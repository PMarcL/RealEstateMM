package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class UserFilterTest {

	private UserFilter userFilter;

	@Before
	public void setUp() throws Exception {
		userFilter = new UserFilter();
	}

	@Test
	public void givenAListOfUsersWhenGetOnlyUserTypeBuyerThenReturnsTheBuyerOnly() {
		User aSeller = aUserMockReturningUserType(UserType.SELLER);
		User aBuyer = aUserMockReturningUserType(UserType.BUYER);
		User anAdmin = aUserMockReturningUserType(UserType.ADMIN);

		Collection<User> users = new ArrayList<>();
		users.add(aSeller);
		users.add(aBuyer);
		users.add(anAdmin);
		int numberOfActiveBuyer = 1;

		Collection<User> actual = userFilter.getUsersWithUserType(users, UserType.BUYER);

		assertTrue(actual.contains(aBuyer));
		assertEquals(numberOfActiveBuyer, actual.size());

	}

	private User aUserMockReturningUserType(String userType) {
		User user = mock(User.class);
		given(user.getUserTypeDescription()).willReturn(userType);
		return user;
	}

}
