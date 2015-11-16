package org.RealEstateMM.services.property;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.RealEstateMM.domain.property.search.PropertySearchParametersParser;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;

public class PropertyService implements PropertyServiceHandler {

	private PropertyDTOAssembler propertyAssembler;
	private Properties properties;
	private PropertySearchParametersParser searchParameterParser;

	public PropertyService() {
		properties = ServiceLocator.getInstance().getService(Properties.class);
		searchParameterParser = ServiceLocator.getInstance().getService(PropertySearchParametersParser.class);
		propertyAssembler = new PropertyDTOAssembler();
	}

	public PropertyService(PropertyDTOAssembler propertyAssembler, Properties properties,
			PropertySearchParametersParser searchParameterParser) {
		this.propertyAssembler = propertyAssembler;
		this.properties = properties;
		this.searchParameterParser = searchParameterParser;
	}

	@Override
	public void uploadProperty(String owner, PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		properties.addProperty(newProperty);
	}

	@Override
	public List<PropertyDTO> getAllProperties(String pseudo) {
		List<Property> allProperties = properties.getAllProperties();
		return buildDTOsFromProperties(allProperties);
	}

	@Override
	public void editPropertyFeatures(String owner, PropertyDTO propertyDTO) {
		Property property = propertyAssembler.fromDTO(propertyDTO);
		PropertyFeatures features = propertyAssembler.getFeaturesFromDTO(propertyDTO);
		properties.editPropertyFeatures(property, features);
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) {
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
	public List<PropertyDTO> getOrderedProperties(String pseudo, String orderBy) {
		PropertySearchParameters searchParam = searchParameterParser.getParsedSearchParameter(orderBy);
		List<Property> orderedProperties = properties.getOrderedProperties(searchParam);
		return buildDTOsFromProperties(orderedProperties);
	}
}
