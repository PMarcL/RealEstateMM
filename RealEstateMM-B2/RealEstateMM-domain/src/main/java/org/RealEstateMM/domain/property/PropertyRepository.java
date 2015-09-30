package org.RealEstateMM.domain.property;

import java.util.Optional;

import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.user.User;

public interface PropertyRepository {
	
	//public Property getPropertyAtAddress(PropertyAddress propertyAddress);
	public Optional<Property> getPropertyAtAddress(PropertyAddress propertyAddress);
	public void add(Property property);
}
