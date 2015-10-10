package org.RealEstateMM.services.property;

import java.util.ArrayList;

import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyFeaturesDTO;

public interface PropertyServiceHandler {

	public void uploadProperty(PropertyDTO propertyInfos);

	public ArrayList<PropertyDTO> getAllProperties();

	public void editPropertyFeatures(PropertyFeaturesDTO featuresDTO);
}
