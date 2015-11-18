package org.RealEstateMM.domain.statistics;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertiesSoldThisYearFilter;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.domain.user.filters.UserLoggedInTheLastSixMonthsFilter;
import org.RealEstateMM.domain.user.filters.UserTypeFilter;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

	private static final ArrayList<User> ALL_USERS = new ArrayList<User>();
	private static final ArrayList<Property> ALL_PROPERTIES = new ArrayList<Property>();

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;

	private UserLoggedInTheLastSixMonthsFilter userLoggedInTheLastSixMonthsFilter;
	private UserTypeFilter userTypeFilter;
	private UserFilterFactory userFilterFactory;

	private PropertiesSoldThisYearFilter propertiesSoldThisYearFilter;
	private PropertyFilterFactory propertyFilterFactory;

	private Statistics statistics;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		propertyRepository = mock(PropertyRepository.class);

		setMocksOfUserFilterFactory();
		setMocksOfPropertiesFilterFactory();

		given(userRepository.getAllUsers()).willReturn(ALL_USERS);
		given(propertyRepository.getAll()).willReturn(ALL_PROPERTIES);

		statistics = new Statistics(propertyRepository, userRepository, userFilterFactory, propertyFilterFactory);
	}

	private void setMocksOfPropertiesFilterFactory() {
		propertiesSoldThisYearFilter = mock(PropertiesSoldThisYearFilter.class);
		propertyFilterFactory = mock(PropertyFilterFactory.class);

		given(propertyFilterFactory.createPropertiesSoldThisYearFilter()).willReturn(propertiesSoldThisYearFilter);
	}

	private void setMocksOfUserFilterFactory() {
		userTypeFilter = mock(UserTypeFilter.class);
		userLoggedInTheLastSixMonthsFilter = mock(UserLoggedInTheLastSixMonthsFilter.class);
		userFilterFactory = mock(UserFilterFactory.class);

		given(userFilterFactory.createLoggedInTheLastSixMonthsFilter()).willReturn(userLoggedInTheLastSixMonthsFilter);
		given(userFilterFactory.createUserTypeFilter()).willReturn(userTypeFilter);
	}

	@Test
	public void whenGetNumberOfPropertySoldThisYearThenReturnsTheSizeOfTheFilteredList() {
		final Collection<Property> PROPERTIES_SOLD_THIS_YEAR = new ArrayList<Property>();
		PROPERTIES_SOLD_THIS_YEAR.add(mock(Property.class));
		PROPERTIES_SOLD_THIS_YEAR.add(mock(Property.class));

		given(propertiesSoldThisYearFilter.getPropertiesSoldThisYear(ALL_PROPERTIES))
				.willReturn(PROPERTIES_SOLD_THIS_YEAR);

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

		given(userTypeFilter.filter(ALL_USERS, AccessLevel.SELLER)).willReturn(BUYERS);
		given(userLoggedInTheLastSixMonthsFilter.filter(BUYERS)).willReturn(ACTIVE_BUYERS);

		int actual = statistics.getNumberOfActiveBuyer();

		assertEquals(ACTIVE_BUYERS.size(), actual);
	}

	@Test
	public void whenGetNumberOfActiveSellerThenReturnsTheSizeOfTheFilteredList() {
		final Collection<User> SELLERS = new ArrayList<User>();
		final Collection<User> ACTIVE_SELLERS = new ArrayList<User>();
		ACTIVE_SELLERS.add(mock(User.class));
		ACTIVE_SELLERS.add(mock(User.class));

		given(userTypeFilter.filter(ALL_USERS, AccessLevel.SELLER)).willReturn(SELLERS);
		// given(userFilter.filter(SELLERS)).willReturn(ACTIVE_SELLERS);
		// TODO this test and ~

		// int actual = statistics.getNumberOfActiveSeller();

		// assertEquals(ACTIVE_SELLERS.size(), actual);
	}

}
