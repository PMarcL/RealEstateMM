package org.RealEstateMM.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class InMemoryPropertyRepository implements org.RealEstateMM.domain.property.PropertyRepository {

	private Map<String, Property> properties;

	public InMemoryPropertyRepository() {
		properties = new HashMap<String, Property>();
	}

	@Override
	public Optional<Property> getPropertyAtAddress(PropertyAddress propertyAddress) {
		if (!propertyExist(propertyAddress)) {
			return Optional.empty();
		}
		return Optional.of(properties.get(propertyAddress.toString()));
	}

	private boolean propertyExist(PropertyAddress propertyAddress) {
		return properties.containsKey(propertyAddress.toString());
	}

	@Override
	public void add(Property property) {
		properties.put(property.getPropertyAddress().toString(), property);
	}

	public int getSize() {
		return properties.size();
	}

	@Override
	public ArrayList<Property> getAllProperties() {
		return new ArrayList<Property>(properties.values());
	}
}
