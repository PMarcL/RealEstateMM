package org.RealEstateMM.persistence.memory;

import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;

public class InMemoryPropertyRepository implements PropertyRepository {

	private ArrayList<Property> properties;

	public InMemoryPropertyRepository() {
		properties = new ArrayList<Property>();
	}

	@Override
	public void add(Property property) {
		properties.add(property);
	}

	public int getSize() {
		return properties.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Property> getAllProperties() {
		return (ArrayList<Property>) properties.clone();
	}

	@Override
	public Optional<Property> getPropertyAtAddress(PropertyAddress address) {
		Optional<Property> property = Optional.empty();
		for (Property p : properties) {
			if (p.getAddress().isEquals(address)) {
				property = Optional.of(p);
				break;
			}
		}
		return property;
	}

	@Override
	public void updateProperty(Property property) {
		for (Property p : properties) {
			if (p.getAddress().isEquals(property.getAddress())) {
				properties.remove(p);
				break;
			}
		}
		properties.add(property);
	}

	@Override
	public ArrayList<Property> getPropertiesFromOwner(String owner) {
		ArrayList<Property> propertiesFromOwner = new ArrayList<Property>();
		for (Property property : properties) {
			if (property.isOwnedBy(owner)) {
				propertiesFromOwner.add(property);
			}
		}

		return propertiesFromOwner;
	}
}
