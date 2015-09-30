package org.RealEstateMM.domain.property;

import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;

public interface PropertyRepository {

	public Optional<Property> getPropertyAtAddress(PropertyAddress propertyAddress);

	public void add(Property property);
}
