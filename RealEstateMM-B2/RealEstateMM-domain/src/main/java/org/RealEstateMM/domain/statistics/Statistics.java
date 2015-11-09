package org.RealEstateMM.domain.statistics;

import java.util.Collection;

import org.RealEstateMM.domain.property.PropertyFilter;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserFilter;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserType;

public class Statistics {

	private PropertyFilter propertyFilter;
	private UserFilter userFilter;
	private PropertyRepository propertyRepository;
	private UserRepository userRepository;

	public Statistics(PropertyRepository propertyRepository, UserRepository userRepository) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;

		propertyFilter = new PropertyFilter(propertyRepository);
		userFilter = new UserFilter();
	}

	public int getNumberOfPropertiesSoldThisYear() {
		return propertyFilter.getPropertiesSoldThisYear().size();
	}

	public int getNumberOfActiveSeller() {
		Collection<User> users = userRepository.getAll();
		Collection<User> sellers = userFilter.getUsersWithUserType(users, UserType.SELLER);
		return userFilter.getActiveUsers(sellers).size();
	}

	public int getNumberOfActiveBuyer() {
		Collection<User> users = userRepository.getAll();
		Collection<User> buyers = userFilter.getUsersWithUserType(users, UserType.BUYER);
		return userFilter.getActiveUsers(buyers).size();
	}

}
