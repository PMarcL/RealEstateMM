package org.RealEstateMM.services.statistics;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class StatisticService {

	private Statistics statistics;

	public StatisticService() {
		PropertyRepository propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		statistics = new Statistics(propertyRepository);
	}

	public int getNumberOfPropertiesSoldThisYear() {
		return statistics.getNumberOfPropertiesSoldThisYear();
	}

}
