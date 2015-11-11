package org.RealEstateMM.domain.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;

public interface PropertyRepository {

	public void add(Property property);

	public ArrayList<Property> getAll();

	public Optional<Property> getPropertyAtAddress(PropertyAddress address);

	public void updateProperty(Property property);

	public List<Property> getPropertiesFromOwner(String owner);
}
