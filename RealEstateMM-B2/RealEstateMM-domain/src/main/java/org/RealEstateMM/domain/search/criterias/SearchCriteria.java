package org.RealEstateMM.domain.search.criterias;

import java.util.List;

import org.RealEstateMM.domain.property.Property;

public interface SearchCriteria {

	public List<Property> filterProperties(List<Property> properties);

}
