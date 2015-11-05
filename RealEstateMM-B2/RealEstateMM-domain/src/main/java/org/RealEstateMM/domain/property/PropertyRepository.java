package org.RealEstateMM.domain.property;

import java.util.ArrayList;
import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;

public interface PropertyRepository {

	public void add(Property property);

	public ArrayList<Property> getAllProperties();

	public Optional<Property> getPropertyAtAddress(PropertyAddress address);

	public void updateProperty(Property property);

	public ArrayList<Property> getPropertiesFromOwner(String owner);
}
