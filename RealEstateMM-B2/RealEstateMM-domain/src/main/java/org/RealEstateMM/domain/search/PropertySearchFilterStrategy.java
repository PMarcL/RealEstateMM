package org.RealEstateMM.domain.search;

import java.util.List;
import org.RealEstateMM.domain.property.Property;

public interface PropertySearchFilterStrategy {

	public List<Property> getFilteredProperties(List<Property> properties);

}
