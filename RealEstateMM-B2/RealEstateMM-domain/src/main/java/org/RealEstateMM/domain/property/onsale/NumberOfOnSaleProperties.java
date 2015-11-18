package org.RealEstateMM.domain.property.onsale;

import java.util.HashMap;

import org.RealEstateMM.domain.property.informations.PropertyType;

public class NumberOfOnSaleProperties {

	private HashMap<String, Integer> mapTypeNumberOfProperties;

	public NumberOfOnSaleProperties(OnSaleProperties onSaleProperties) {
		mapTypeNumberOfProperties = new HashMap<String, Integer>();

		for (PropertyType type : PropertyType.values()) {
			int numberOfProperties = onSaleProperties.findOnSalePropertiesByType(type).size();
			mapTypeNumberOfProperties.put(PropertyType.getStringFromType(type), numberOfProperties);
		}
	}

	public HashMap<String, Integer> getMapTypeNumberOfProperties() {
		return mapTypeNumberOfProperties;
	}
}
