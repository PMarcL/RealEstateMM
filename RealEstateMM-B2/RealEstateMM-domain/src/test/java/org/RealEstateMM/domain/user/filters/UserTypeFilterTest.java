package org.RealEstateMM.domain.user.filters;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Before;
import org.junit.Test;

public class UserTypeFilterTest {

	private final User A_SELLER = aUserMockReturningUserType(AccessLevel.SELLER);
	private final User ANOTHER_SELLER = aUserMockReturningUserType(AccessLevel.SELLER);
	private final User A_BUYER = aUserMockReturningUserType(AccessLevel.BUYER);
	private final User AN_ADMIN = aUserMockReturningUserType(AccessLevel.ADMIN);

	private UserTypeFilter userTypeFilter;

	@Before
	public void setUp() throws Exception {
		userTypeFilter = new UserTypeFilter();
	}

	@Test
	public void givenAListOfUsersWhenGetOnlyUserTypeBuyerThenReturnsTheBuyerOnly() {
		Collection<User> users = new ArrayList<>();
		users.add(A_SELLER);
		users.add(A_BUYER);
		users.add(AN_ADMIN);
		int numberOfBuyer = 1;

		Collection<User> actual = userTypeFilter.filter(users, AccessLevel.BUYER);

		assertTrue(actual.contains(A_BUYER));
		assertEquals(numberOfBuyer, actual.size());
	}

	@Test
	public void givenAListOfUsersWhenGetOnlyUserTypeSellerThenReturnsTheSellerOnly() {
		Collection<User> users = new ArrayList<>();
		users.add(A_SELLER);
		users.add(ANOTHER_SELLER);
		users.add(A_BUYER);
		users.add(AN_ADMIN);
		int numberOfSeller = 2;

		Collection<User> actual = userTypeFilter.filter(users, AccessLevel.SELLER);

		assertTrue(actual.contains(A_SELLER));
		assertTrue(actual.contains(ANOTHER_SELLER));
		assertEquals(numberOfSeller, actual.size());
	}

	private User aUserMockReturningUserType(AccessLevel userType) {
		User user = mock(User.class);
		given(user.getRoleDescription()).willReturn(userType);
		return user;
	}

}