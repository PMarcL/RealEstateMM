package org.RealEstateMM.services.property;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.dtos.property.PropertyAddressDTOAssembler;
import org.RealEstateMM.services.dtos.property.PropertyDTO;
import org.RealEstateMM.services.dtos.property.PropertyDTOAssembler;

import com.google.gson.Gson;

public class PropertyService {

	private PropertyRepository propertyRepository;
	private PropertyDTOAssembler propertyAssembler;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyDTOAssembler();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyDTOAssembler propertyAssembler,
			PropertyAddressDTOAssembler addressAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
	}

	public void uploadProperty(PropertyDTO propertyInfos) {
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}

	public void editPropertyInfos(Property property) {
		// TODO Implement
	}
	
	public String getAllProperties()
	{
		Gson gson = new Gson();

		String json = gson.toJson(propertyRepository.getAllProperties());
		return json;
	}
}
