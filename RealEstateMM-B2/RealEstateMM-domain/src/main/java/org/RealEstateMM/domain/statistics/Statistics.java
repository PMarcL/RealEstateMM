package org.RealEstateMM.domain.statistics;

import org.RealEstateMM.domain.property.PropertyFilter;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.UserFilter;
import org.RealEstateMM.domain.user.UserRepository;

public class Statistics {

	private PropertyFilter propertyFilter;
	private UserFilter userFilter;

	public Statistics(PropertyRepository propertyRepository, UserRepository userRepository) {
		propertyFilter = new PropertyFilter(propertyRepository);
		userFilter = new UserFilter(userRepository);
	}

	public int getNumberOfPropertiesSoldThisYear() {
		return propertyFilter.getPropertiesSoldThisYear().size();
	}

	public int getNumberOfActiveSeller() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfActiveBuyer() {
		return userFilter.getActiveBuyer().size();
	}

}
