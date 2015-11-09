package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

// public class UserFilterTest {
//
// private UserFilter userFilter;
//
// @Before
// public void setUp() throws Exception {
// userFilter = new UserFilter();
// }
//
// @Test
// public void givenAListOfUsersWhenGetBuyersThenReturnsTheBuyersOnly() {
// boolean whatever = false;
// User aSeller = aUserMockReturningTypeAndIsActive(UserType.SELLER, whatever);
// User anActiveBuyer = aUserMockReturningTypeAndIsActive(UserType.BUYER, true);
// User anInactiveBuyer = aUserMockReturningTypeAndIsActive(UserType.BUYER,
// false);
// User anAdmin = aUserMockReturningTypeAndIsActive(UserType.ADMIN, whatever);
//
// ArrayList<User> users = new ArrayList<>();
// users.add(aSeller);
// users.add(anActiveBuyer);
// users.add(anInactiveBuyer);
// users.add(anAdmin);
// int numberOfActiveBuyer = 1;
//
// Collection<User> actual = userFilter.getBuyers(users);
//
// assertTrue(actual.contains(aBuyer));
// assertEquals(numberOfActiveBuyer, actual.size());
// }
//
// private User aUserMockReturningTypeAndIsActive(String userType, boolean
// isActive) {
// User user = mock(User.class);
// given(user.isActive()).willReturn(isActive);
// given(user.getUserTypeDescription()).willReturn(userType);
// return user;
// }
// }
