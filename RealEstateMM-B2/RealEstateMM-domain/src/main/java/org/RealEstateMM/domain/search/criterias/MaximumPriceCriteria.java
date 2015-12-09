package org.RealEstateMM.domain.search.criterias;

import java.util.List;
import java.util.stream.Collectors;

import org.RealEstateMM.domain.property.Property;

public class MaximumPriceCriteria implements SearchCriteria {

	private int maximumPrice;

	public MaximumPriceCriteria(int maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@Override
	public List<Property> filterProperties(List<Property> properties) {
		return properties.stream().filter(p -> p.isLessExpensive(maximumPrice)).collect(Collectors.toList());
	}

}
