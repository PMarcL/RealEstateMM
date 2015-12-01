package org.RealEstateMM.domain.search;

import java.util.List;
import org.RealEstateMM.domain.property.Property;

public interface PropertyOrderingStrategy {

	public List<Property> getOrderedProperties(List<Property> properties);

}
