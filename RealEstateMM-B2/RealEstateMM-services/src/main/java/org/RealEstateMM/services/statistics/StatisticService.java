package org.RealEstateMM.services.statistics;

import java.util.HashMap;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.locator.ServiceLocator;

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
	
	public HashMap<String, Integer> getNumberOfOnSalePropertiesPerCategory(){
		
		return statistics.getNumberOfPropertiesOnSalePerCategory();
	}
	
	public int getNumberOfSellerWithAnOnSaleProperty(){
		return statistics.getNumberOfSellersWithOnSaleProperties();
	}

}
