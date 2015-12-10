package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.services.statistics.StatisticService;

public class StatsServiceFactory {

	public StatisticService create(PropertyRepository propertyRepository, UserRepository userRepository) {
		Statistics statistics = new Statistics(propertyRepository, userRepository, new UserFilterFactory(),
				new PropertyFilterFactory());
		return new StatisticService(statistics);
	}

}
