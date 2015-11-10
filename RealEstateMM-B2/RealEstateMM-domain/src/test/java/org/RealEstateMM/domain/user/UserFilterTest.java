package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Before;
import org.junit.Test;

public class UserFilterTest {

	private final User A_SELLER = aUserMockReturningUserType(AccessLevel.SELLER);
	private final User ANOTHER_SELLER = aUserMockReturningUserType(AccessLevel.SELLER);
	private final User A_BUYER = aUserMockReturningUserType(AccessLevel.BUYER);
	private final User AN_ADMIN = aUserMockReturningUserType(AccessLevel.ADMIN);

	private final User AN_ACTIVE_USER = aUserMockReturningIsActive(true);
	private final User AN_INACTIVE_USER = aUserMockReturningIsActive(false);

	private UserFilter userFilter;

	@Before
	public void setUp() throws Exception {
		userFilter = new UserFilter();
	}

	@Test
	public void givenAListOfUsersWhenGetOnlyUserTypeBuyerThenReturnsTheBuyerOnly() {
		Collection<User> users = new ArrayList<>();
		users.add(A_SELLER);
		users.add(A_BUYER);
		users.add(AN_ADMIN);
		int numberOfBuyer = 1;

		Collection<User> actual = userFilter.getUsersWithUserType(users, AccessLevel.BUYER);

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

		Collection<User> actual = userFilter.getUsersWithUserType(users, AccessLevel.SELLER);

		assertTrue(actual.contains(A_SELLER));
		assertTrue(actual.contains(ANOTHER_SELLER));
		assertEquals(numberOfSeller, actual.size());
	}

	@Test
	public void givenAListOfUsersWhenGetActiveUsersThenReturnsOnlyActiveUser() {
		Collection<User> users = new ArrayList<>();
		users.add(AN_ACTIVE_USER);
		users.add(AN_INACTIVE_USER);
		int numberOfActiveUser = 1;

		Collection<User> actual = userFilter.getActiveUsers(users);

		assertTrue(actual.contains(AN_ACTIVE_USER));
		assertEquals(numberOfActiveUser, actual.size());

	}

	private User aUserMockReturningIsActive(boolean isActive) {
		User user = mock(User.class);
		given(user.isActive()).willReturn(isActive);
		return user;
	}

	private User aUserMockReturningUserType(AccessLevel userType) {
		User user = mock(User.class);
		given(user.getRoleDescription()).willReturn(userType);
		return user;
	}

}
