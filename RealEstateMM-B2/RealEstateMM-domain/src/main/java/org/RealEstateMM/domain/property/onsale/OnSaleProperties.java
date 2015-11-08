package org.RealEstateMM.domain.property.onsale;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyStatus;
import org.RealEstateMM.domain.property.informations.PropertyType;

public class OnSaleProperties {

	private ArrayList<Property> onSaleProperties;
	private PropertyRepository propertyRepository;

	public OnSaleProperties(PropertyRepository repository) {
		onSaleProperties = new ArrayList<Property>();
		propertyRepository = repository;
		findOnSaleProperties();
	}

	public List<Property> findOnSalePropertiesByType(PropertyType type) {
		return onSaleProperties.stream().filter(p -> p.getType() == type).collect(Collectors.toList());
	}

	private void findOnSaleProperties() {
		onSaleProperties = (ArrayList<Property>) propertyRepository.getAllProperties().stream()
				.filter(p -> p.getPropertyStatus() == PropertyStatus.ONSALE).collect(Collectors.toList());
	}
}
