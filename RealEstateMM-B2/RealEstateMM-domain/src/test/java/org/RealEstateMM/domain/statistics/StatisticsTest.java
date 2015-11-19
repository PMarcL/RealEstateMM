package org.RealEstateMM.domain.statistics;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertiesSoldThisYearFilter;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.property.filters.PropertyStatusFilter;
import org.RealEstateMM.domain.property.filters.PropertyTypeFilter;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;
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
	private PropertyStatusFilter propertyStatusFilter;
	private PropertyTypeFilter propertyTypeFilter;
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

	private void setMocksOfUserFilterFactory() {
		userTypeFilter = mock(UserTypeFilter.class);
		userLoggedInTheLastSixMonthsFilter = mock(UserLoggedInTheLastSixMonthsFilter.class);
		userFilterFactory = mock(UserFilterFactory.class);

		given(userFilterFactory.createLoggedInTheLastSixMonthsFilter()).willReturn(userLoggedInTheLastSixMonthsFilter);
		given(userFilterFactory.createUserTypeFilter()).willReturn(userTypeFilter);
	}

	private void setMocksOfPropertiesFilterFactory() {
		propertiesSoldThisYearFilter = mock(PropertiesSoldThisYearFilter.class);
		propertyStatusFilter = mock(PropertyStatusFilter.class);
		propertyTypeFilter = mock(PropertyTypeFilter.class);
		propertyFilterFactory = mock(PropertyFilterFactory.class);

		given(propertyFilterFactory.createPropertiesSoldThisYearFilter()).willReturn(propertiesSoldThisYearFilter);
		given(propertyFilterFactory.createPropertyStatusFilter()).willReturn(propertyStatusFilter);
		given(propertyFilterFactory.createPropertyTypeFilter()).willReturn(propertyTypeFilter);
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
	public void whenGetNumberOfActiveSellerThenReturnsTheNumberOfSellerWithAtLeastOnePropertyOnSale() {
		String owner = "Joe The Owner";
		Property aProperty = aMockPropertyWithOwner(owner);
		Property anotherPropertyWithTheSameOwner = aMockPropertyWithOwner(owner);

		Collection<Property> onSaleProperties = new ArrayList<Property>();
		onSaleProperties.add(aProperty);
		onSaleProperties.add(anotherPropertyWithTheSameOwner);
		int differentSellerWithOnePropertyOnSale = 1;

		given(propertyStatusFilter.filter(ALL_PROPERTIES, PropertyStatus.ON_SALE)).willReturn(onSaleProperties);

		int actual = statistics.getNumberOfActiveSeller();

		assertEquals(differentSellerWithOnePropertyOnSale, actual);

	}

	private Property aMockPropertyWithOwner(String owner) {
		Property property = mock(Property.class);
		given(property.getOwner()).willReturn(owner);
		return property;
	}

	@Test
	public void givenAPropertiesWhenGetNumberOfPropertiesOnSalePerTypeThenReturnsTheCorrectAmountOfOnSalePropertyForEachType() {
		Property aCommercialProperty = aMockPropertyWithType(PropertyType.COMMERCIAL);
		Property anotherCommercialProperty = aMockPropertyWithType(PropertyType.COMMERCIAL);
		Property aFarmProperty = aMockPropertyWithType(PropertyType.FARM);

		Collection<Property> commercials = Arrays.asList(aCommercialProperty, anotherCommercialProperty);

		Collection<Property> onSaleProperties = new ArrayList<Property>();
		onSaleProperties.add(aCommercialProperty);
		onSaleProperties.add(anotherCommercialProperty);
		onSaleProperties.add(aFarmProperty);

		given(propertyStatusFilter.filter(ALL_PROPERTIES, PropertyStatus.ON_SALE)).willReturn(onSaleProperties);
		given(propertyTypeFilter.filter(onSaleProperties, PropertyType.COMMERCIAL)).willReturn(commercials);

		int actualNumberOfOnSaleCommercials = statistics.getNumberOfPropertiesOnSalePerType(PropertyType.COMMERCIAL);
		int actualNumberOfOnSaleHouses = statistics.getNumberOfPropertiesOnSalePerType(PropertyType.HOUSE);
		System.out.println(actualNumberOfOnSaleCommercials);
		System.out.println(actualNumberOfOnSaleHouses);

		assertEquals(2, actualNumberOfOnSaleCommercials);
		assertEquals(0, actualNumberOfOnSaleHouses);
	}

	private Property aMockPropertyWithType(PropertyType type) {
		Property property = mock(Property.class);
		given(property.getType()).willReturn(type);
		return property;
	}

}
