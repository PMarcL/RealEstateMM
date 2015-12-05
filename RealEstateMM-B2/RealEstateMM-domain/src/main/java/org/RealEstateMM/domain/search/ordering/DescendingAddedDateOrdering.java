package org.RealEstateMM.domain.search.ordering;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.search.PropertyOrderingStrategy;

public class DescendingAddedDateOrdering implements PropertyOrderingStrategy {

	@Override
	public List<Property> sort(List<Property> properties) {
		Comparator<Property> mostRecentPropertyFirst = Comparator.comparing(Property::getCreationDate).reversed();
		Collections.sort(properties, mostRecentPropertyFirst);
		return properties;
	}
}
