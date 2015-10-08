package org.RealEstateMM.domain.property;

import java.util.ArrayList;
import java.util.Optional;

public interface PropertyRepository {

	public void add(Property property);

	public ArrayList<Property> getAllProperties();

	public Optional<Property> getPropertyWithZipCode(String zipcode);

	public void updateProperty(Property property);
}
