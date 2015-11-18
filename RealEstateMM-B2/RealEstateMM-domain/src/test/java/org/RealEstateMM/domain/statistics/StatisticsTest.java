package org.RealEstateMM.domain.statistics;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyFilter;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserFilter;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

	private static final ArrayList<User> ALL_USERS = new ArrayList<User>();
	private static final ArrayList<Property> ALL_PROPERTIES = new ArrayList<Property>();

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private UserFilter userFilter;
	private PropertyFilter propertyFilter;
	private Statistics statistics;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		propertyRepository = mock(PropertyRepository.class);
		userFilter = mock(UserFilter.class);
		propertyFilter = mock(PropertyFilter.class);

		given(userRepository.getAllUsers()).willReturn(ALL_USERS);
		given(propertyRepository.getAll()).willReturn(ALL_PROPERTIES);

		statistics = new Statistics(propertyRepository, userRepository, userFilter, propertyFilter);
	}

	@Test
	public void whenGetNumberOfPropertySoldThisYearThenReturnsTheSizeOfTheFilteredList() {
		final Collection<Property> PROPERTIES_SOLD_THIS_YEAR = new ArrayList<Property>();
		PROPERTIES_SOLD_THIS_YEAR.add(mock(Property.class));
		PROPERTIES_SOLD_THIS_YEAR.add(mock(Property.class));

		given(propertyFilter.getPropertiesSoldThisYear(ALL_PROPERTIES)).willReturn(PROPERTIES_SOLD_THIS_YEAR);

		int actual = statistics.getNumberOfPropertiesSoldThisYear();

		assertEquals(PROPERTIES_SOLD_THIS_YEAR.size(), actual);
	}

	@Test
	public void whenGetNumberOfActiveBuyerThenReturnsTheSizeOfTheFilteredList() {
		final Collection<User> BUYERS = new ArrayList<User>();
		final Collection<User> ACTIVE_BUYERS = new ArrayList<User>();
		ACTIVE_BUYERS.add(mock(User.class));
		ACTIVE_BUYERS.add(mock(User.class));
		ACTIVE_BUYERS.add(mock(User.class));

		given(userFilter.getUsersWithUserType(ALL_USERS, AccessLevel.SELLER)).willReturn(BUYERS);
		given(userFilter.getUsersLastLoggedInTheLast6Months(BUYERS)).willReturn(ACTIVE_BUYERS);

		int actual = statistics.getNumberOfActiveBuyer();

		assertEquals(ACTIVE_BUYERS.size(), actual);
	}

	@Test
	public void whenGetNumberOfActiveSellerThenReturnsTheSizeOfTheFilteredList() {
		final Collection<User> SELLERS = new ArrayList<User>();
		final Collection<User> ACTIVE_SELLERS = new ArrayList<User>();
		ACTIVE_SELLERS.add(mock(User.class));
		ACTIVE_SELLERS.add(mock(User.class));

		given(userFilter.getUsersWithUserType(ALL_USERS, AccessLevel.SELLER)).willReturn(SELLERS);
		given(userFilter.getUsersLastLoggedInTheLast6Months(SELLERS)).willReturn(ACTIVE_SELLERS);

		int actual = statistics.getNumberOfActiveSeller();

		assertEquals(ACTIVE_SELLERS.size(), actual);
	}

}
