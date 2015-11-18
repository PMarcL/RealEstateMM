package org.RealEstateMM.domain.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertiesSoldThisYearFilter;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.property.filters.PropertyStatusFilter;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.onsale.NumberOfOnSaleProperties;
import org.RealEstateMM.domain.property.onsale.OnSaleProperties;
import org.RealEstateMM.domain.property.onsale.SellersWithOnSaleProperty;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.domain.user.filters.UserLoggedInTheLastSixMonthsFilter;
import org.RealEstateMM.domain.user.filters.UserTypeFilter;

public class Statistics {

	private PropertyRepository propertyRepository;
	private UserRepository userRepository;

	private UserLoggedInTheLastSixMonthsFilter loggedInTheLastSixMonthsFilter;
	private UserTypeFilter userTypeFilter;

	private PropertiesSoldThisYearFilter propertiesSoldThisYearFilter;
	private PropertyStatusFilter propertyStatusFilter;

	public Statistics(PropertyRepository propertyRepository, UserRepository userRepository,
			UserFilterFactory userFilterFactory, PropertyFilterFactory propertyFilterFactory) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;

		initializeUserFilters(userFilterFactory);
		initializePropertyFilters(propertyFilterFactory);
	}

	private void initializeUserFilters(UserFilterFactory userFilterFactory) {
		loggedInTheLastSixMonthsFilter = userFilterFactory.createLoggedInTheLastSixMonthsFilter();
		userTypeFilter = userFilterFactory.createUserTypeFilter();
	}

	private void initializePropertyFilters(PropertyFilterFactory propertyFilterFactory) {
		propertiesSoldThisYearFilter = propertyFilterFactory.createPropertiesSoldThisYearFilter();
		propertyStatusFilter = propertyFilterFactory.createPropertyStatusFilter();
	}

	public int getNumberOfPropertiesSoldThisYear() {
		Collection<Property> properties = propertyRepository.getAll();
		return propertiesSoldThisYearFilter.getPropertiesSoldThisYear(properties).size();
	}

	public HashMap<String, Integer> getNumberOfPropertiesOnSalePerCategory() {
		OnSaleProperties onSaleProperties = new OnSaleProperties(propertyRepository);
		NumberOfOnSaleProperties numberOfPropertiesByCategory = new NumberOfOnSaleProperties(onSaleProperties);
		return numberOfPropertiesByCategory.getMapTypeNumberOfProperties();
	}

	public int getNumberOfActiveSeller() {
		ArrayList<Property> properties = propertyRepository.getAll();
		Collection<Property> onSaleProperties = propertyStatusFilter.filter(properties, PropertyStatus.ON_SALE);
		return (int) onSaleProperties.stream().map(p -> p.getOwner()).distinct().count();

	}

	public int getNumberOfActiveBuyer() {
		Collection<User> users = userRepository.getAllUsers();
		Collection<User> buyers = userTypeFilter.filter(users, AccessLevel.BUYER);
		return loggedInTheLastSixMonthsFilter.filter(buyers).size();
	}

	// TODO remove this for getNumberOfActiveSeller
	public int getNumberOfSellersWithOnSaleProperties() {
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(propertyRepository);
		return sellers.findNumberOfSellerWithOnSaleProperty();
	}

}
