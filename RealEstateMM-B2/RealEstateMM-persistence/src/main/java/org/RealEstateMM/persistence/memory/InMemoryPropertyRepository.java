package org.RealEstateMM.persistence.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyAddress;
import org.RealEstateMM.domain.property.PropertyRepository;

public class InMemoryPropertyRepository extends PropertyRepository {

	private Map<String, Property> properties;

	public InMemoryPropertyRepository() {
		properties = new HashMap<String, Property>();
	}

	@Override
	public Optional<Property> getPropertyAtAddress(String streetAddress, String cityAddress) {
		// if (!propertyExist(propertyAddress)) {
		// return Optional.empty();
		// }
		// return Optional.of(properties.get(propertyAddress.toString()));
		//
		return null;
	}
	// TODO Fix

	private boolean propertyExist(PropertyAddress propertyAddress) {
		return properties.containsKey(propertyAddress.toString());
	}

	@Override
	public void add(Property property) {
		properties.put(property.propertyAddress.toString(), property);
	}

	public int getSize() {
		return properties.size();
	}

	public List<Property> getAllProperties() {
		return new ArrayList<Property>(properties.values());
	}

	@Override
	protected boolean contains(String streetAddress, String cityAddress) {
		// TODO Auto-generated method stub
		return false;
	}
}
