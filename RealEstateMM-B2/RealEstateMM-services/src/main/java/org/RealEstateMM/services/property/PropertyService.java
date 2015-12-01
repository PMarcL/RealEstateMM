package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;

public class PropertyService implements PropertyServiceHandler {

	private PropertyDTOAssembler propertyAssembler;
	private Properties properties;

	public PropertyService() {
		properties = ServiceLocator.getInstance().getService(Properties.class);
		propertyAssembler = new PropertyDTOAssembler();
	}

	public PropertyService(PropertyDTOAssembler propertyAssembler, Properties properties) {
		this.propertyAssembler = propertyAssembler;
		this.properties = properties;
	}

	@Override
	public void uploadProperty(String owner, PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		properties.addProperty(newProperty);
	}

	@Override
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) {
		Property property = propertyAssembler.fromDTO(propertyDTO);
		PropertyFeatures features = propertyAssembler.getFeaturesFromDTO(propertyDTO);
		properties.editPropertyFeatures(property, features);
	}
}
