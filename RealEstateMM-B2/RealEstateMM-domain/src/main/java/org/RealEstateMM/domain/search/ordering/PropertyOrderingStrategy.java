package org.RealEstateMM.domain.search.ordering;

import java.util.List;
import org.RealEstateMM.domain.property.Property;

public interface PropertyOrderingStrategy {

	public List<Property> sort(List<Property> properties);

}
