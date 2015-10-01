package org.RealEstateMM.domain.property;

import java.util.List;
import java.util.Optional;

public abstract class PropertyRepository {

	public abstract Optional<Property> getPropertyAtAddress(String streetAddress, String cityAddress);

	public abstract void add(Property property);

	public abstract List<Property> getAllProperties();

	protected abstract boolean contains(String streetAddress, String cityAddress);
}
