package org.RealEstateMM.domain.statistics;

import org.RealEstateMM.domain.property.PropertyFilter;
import org.RealEstateMM.domain.property.PropertyRepository;

public class Statistics {

	private PropertyFilter propertyFilter;

	public Statistics(PropertyRepository propertyRepository) {
		propertyFilter = new PropertyFilter(propertyRepository);
	}

	public int getNumberOfPropertiesSoldThisYear() {
		return propertyFilter.getPropertiesSoldThisYear().size();
	}

	public int getNumberOfActiveSeller() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfActiveBuyer() {
		// TODO Auto-generated method stub
		return 0;
	}

}
