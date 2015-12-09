package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class MinimumPriceCriteria implements SearchCriteria {

	private int minimumPrice;

	public MinimumPriceCriteria(int minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	@Override
	public List<Property> filterProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.isMoreExpensive(minimumPrice)).collect(Collectors.toList());
	}

}
