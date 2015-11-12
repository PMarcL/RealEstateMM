package org.RealEstateMM.services.property;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertySearchFilter;
import org.RealEstateMM.servicelocator.ServiceLocator;
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
		// TODO ajouter vérification des droits utilisateurs
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		properties.addProperty(newProperty);
	}

	@Override
	public List<PropertyDTO> getAllProperties(String pseudo) {
		// TODO ajouter vérification des droits utilisateurs
		List<Property> allProperties = properties.getAllProperties();
		return buildDTOsFromProperties(allProperties);
	}

	@Override
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) {
		// TODO ajouter vérification des droits utilisateurs
		Property property = propertyAssembler.fromDTO(propertyDTO);
		PropertyFeatures features = propertyAssembler.getFeaturesFromDTO(propertyDTO);
		properties.editPropertyFeatures(property, features);
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) {
		// TODO ajouter vérification des droits utilisateurs
		List<Property> ownersProperties = properties.getPropertiesFromOwner(owner);
		return buildDTOsFromProperties(ownersProperties);
	}

	private List<PropertyDTO> buildDTOsFromProperties(List<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = propertyAssembler.toDTO(property);
			propertiesDTO.add(dto);
		}
		return propertiesDTO;
	}

	@Override
	public List<PropertyDTO> getOrderedProperties(String pseudo, PropertySearchFilter orderBy) {
		List<Property> orderedProperties = properties.getOrderedProperties(orderBy);
		return buildDTOsFromProperties(orderedProperties);
	}
}
