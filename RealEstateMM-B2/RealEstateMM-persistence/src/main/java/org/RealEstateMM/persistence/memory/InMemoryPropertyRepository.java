package org.RealEstateMM.persistence.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class InMemoryPropertyRepository implements PropertyRepository {

	private Map<PropertyAddress, Property> properties;

	public InMemoryPropertyRepository() {
		properties = new HashMap<PropertyAddress, Property>();
	}

	@Override
	public void add(Property property) {
		properties.put(property.getAddress(), property);
	}

	public int getSize() {
		return properties.size();
	}

	@Override
	public ArrayList<Property> getAllProperties() {
		return new ArrayList<Property>(properties.values());
	}

	@Override
	public Optional<Property> getPropertyAtAddress(PropertyAddress address) {
		Property property = properties.get(address);
		return Optional.of(property);
	}

	@Override
	public void updateProperty(Property property) {
		properties.put(property.getAddress(), property);
	}

	@Override
	public ArrayList<Property> getPropertiesFromOwner(String owner) {
		ArrayList<Property> propertiesFromOwner = new ArrayList<Property>();
		for (Property property : properties.values()) {
			if (property.isOwnedBy(owner)) {
				propertiesFromOwner.add(property);
			}
		}

		return propertiesFromOwner;
	}
}
