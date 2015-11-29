package org.RealEstateMM.services.statistics;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class StatisticService {

	private Statistics statistics;

	public StatisticService() {
		PropertyRepository propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		UserRepository userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		statistics = new Statistics(propertyRepository, userRepository);
	}

	public int getNumberOfPropertiesSoldThisYear() {
		return statistics.getNumberOfPropertiesSoldThisYear();
	}

	public int getNumberOfActiveSeller() {
		return statistics.getNumberOfActiveSeller();
	}

	public int getNumberOfActiveBuyer() {
		return statistics.getNumberOfActiveBuyer();
	}

	public int getNumberOfOnSalePropertiesPerType(PropertyType type) {
		return statistics.getNumberOfPropertiesOnSalePerType(type);
	}

}
