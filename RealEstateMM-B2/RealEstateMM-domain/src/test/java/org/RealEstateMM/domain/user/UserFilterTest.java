package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class UserFilterTest {

	private UserRepository userRepository;
	private UserFilter userFilter;

	@Before
	public void setUp() throws Exception {
		userRepository = mock(UserRepository.class);
		userFilter = new UserFilter(userRepository);
	}

	@Test
	public void givenAListOfUsersWhenGetActiveBuyerThenReturnsTheBuyerLastLogged6MonthsAgo() {
		boolean whatever = false;
		User aSeller = aUserMockReturningTypeAndIsActive(UserType.SELLER, whatever);
		User anActiveBuyer = aUserMockReturningTypeAndIsActive(UserType.BUYER, true);
		User anInactiveBuyer = aUserMockReturningTypeAndIsActive(UserType.BUYER, false);
		User anAdmin = aUserMockReturningTypeAndIsActive(UserType.ADMIN, whatever);

		ArrayList<User> users = new ArrayList<>();
		users.add(aSeller);
		users.add(anActiveBuyer);
		users.add(anInactiveBuyer);
		users.add(anAdmin);
		int numberOfActiveBuyer = 1;

		given(userRepository.getAll()).willReturn(users);

		ArrayList<User> actual = userFilter.getActiveBuyer();

		assertTrue(actual.contains(anActiveBuyer));
		assertEquals(numberOfActiveBuyer, actual.size());
	}

	private User aUserMockReturningTypeAndIsActive(String userType, boolean isActive) {
		User user = mock(User.class);
		given(user.isActive()).willReturn(isActive);
		given(user.getUserTypeDescription()).willReturn(userType);
		return user;
	}
}
