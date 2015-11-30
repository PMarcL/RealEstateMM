package org.RealEstateMM.services.property;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTOAssembler;

public class PropertyService implements PropertyServiceHandler {

	private PropertyAssembler propertyAssembler;
	private PropertySearchParametersDTOAssembler searchParamAssembler;
	private Properties properties;

	public PropertyService(PropertyAssembler propertyAssembler,
			PropertySearchParametersDTOAssembler searchParamAssembler) {
		this.propertyAssembler = propertyAssembler;
		this.searchParamAssembler = searchParamAssembler;
		this.properties = ServiceLocator.getInstance().getService(Properties.class);
	}

	@Override
	public void uploadProperty(String owner, PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		properties.addProperty(newProperty);
	}

	@Override
	public List<PropertyDTO> getPropertiesSearchResult(String pseudo, PropertySearchParametersDTO searchParamsDTO)
			throws InvalidSearchParameterException {
		PropertySearchParameters searchParams = searchParamAssembler.fromDTO(searchParamsDTO);
		List<Property> allProperties = properties.getPropertiesSearchResults(searchParams);
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
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO addressDTO)
			throws PropertyNotFoundException {
		PropertyAddress address = propertyAssembler.getAddressFromDTO(addressDTO);
		Property property = properties.getPropertyAtAddress(address);
		return propertyAssembler.toDTO(property);
	}
}
