package org.RealEstateMM.domain.statistics;

import java.util.Collection;
import java.util.HashMap;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyFilter;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.onsale.NumberOfOnSaleProperties;
import org.RealEstateMM.domain.property.onsale.OnSaleProperties;
import org.RealEstateMM.domain.property.onsale.SellersWithOnSaleProperty;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserFilter;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;

public class Statistics {

	private PropertyFilter propertyFilter;
	private UserFilter userFilter;
	private PropertyRepository propertyRepository;
	private UserRepository userRepository;

	public Statistics(PropertyRepository propertyRepository, UserRepository userRepository) {
		propertyFilter = new PropertyFilter();
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;

		propertyFilter = new PropertyFilter();
		userFilter = new UserFilter();
	}

	public Statistics(PropertyRepository propertyRepository, UserRepository userRepository, UserFilter userFilter,
			PropertyFilter propertyFilter) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
		this.userFilter = userFilter;
		this.propertyFilter = propertyFilter;
	}

	public int getNumberOfPropertiesSoldThisYear() {
		Collection<Property> properties = propertyRepository.getAll();
		return propertyFilter.getPropertiesSoldThisYear(properties).size();
	}

	public HashMap<String, Integer> getNumberOfPropertiesOnSalePerCategory() {
		OnSaleProperties onSaleProperties = new OnSaleProperties(propertyRepository);
		NumberOfOnSaleProperties numberOfPropertiesByCategory = new NumberOfOnSaleProperties(onSaleProperties);
		return numberOfPropertiesByCategory.getMapTypeNumberOfProperties();
	}

	public int getNumberOfActiveSeller() {
		Collection<User> users = userRepository.getAllUsers();
		Collection<User> sellers = userFilter.getUsersWithUserType(users, AccessLevel.SELLER);
		return userFilter.getActiveUsers(sellers).size();
	}

	public int getNumberOfActiveBuyer() {
		Collection<User> users = userRepository.getAllUsers();
		Collection<User> buyers = userFilter.getUsersWithUserType(users, AccessLevel.BUYER);
		return userFilter.getActiveUsers(buyers).size();
	}
	
	public int getNumberOfSellersWithOnSaleProperties(){
		SellersWithOnSaleProperty sellers = new SellersWithOnSaleProperty(propertyRepository);
		return sellers.findNumberOfSellerWithOnSaleProperty();
	}

}
