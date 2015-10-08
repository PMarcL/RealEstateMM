package org.RealEstateMM.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.RealEstateMM.domain.property.Property;

public class InMemoryPropertyRepository implements org.RealEstateMM.domain.property.PropertyRepository {

	private Map<String, Property> properties;

	public InMemoryPropertyRepository() {
		properties = new HashMap<String, Property>();
	}

	@Override
	public void add(Property property) {
		properties.put(property.getZipCode(), property);
	}

	public int getSize() {
		return properties.size();
	}

	@Override
	public ArrayList<Property> getAllProperties() {
		return new ArrayList<Property>(properties.values());
	}

	@Override
	public Optional<Property> getPropertyWithZipCode(String zipcode) {
		Property property = properties.get(zipcode);
		return Optional.of(property);
	}

	@Override
	public void updateProperty(Property property) {
		properties.put(property.getZipCode(), property);
	}
}
