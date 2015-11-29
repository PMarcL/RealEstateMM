package org.RealEstateMM.services.statistics;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.services.locator.ServiceLocator;

public class StatisticService {

	private Statistics statistics;

	public StatisticService() {
		PropertyRepository propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		UserRepository userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		UserFilterFactory userFilterFactory = ServiceLocator.getInstance().getService(UserFilterFactory.class);
		PropertyFilterFactory propertyFilterFactory = ServiceLocator.getInstance()
				.getService(PropertyFilterFactory.class);
		statistics = new Statistics(propertyRepository, userRepository, userFilterFactory, propertyFilterFactory);
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
