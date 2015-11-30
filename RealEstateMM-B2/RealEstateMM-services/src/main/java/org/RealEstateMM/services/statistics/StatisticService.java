package org.RealEstateMM.services.statistics;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class StatisticService {

	private Statistics statistics;

	public StatisticService() {
		this.statistics = ServiceLocator.getInstance().getService(Statistics.class);
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
