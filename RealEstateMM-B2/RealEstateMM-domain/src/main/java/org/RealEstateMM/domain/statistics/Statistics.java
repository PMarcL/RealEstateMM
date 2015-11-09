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

}
