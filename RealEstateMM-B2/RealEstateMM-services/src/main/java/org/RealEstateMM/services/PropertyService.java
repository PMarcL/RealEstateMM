package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.services.dtos.property.PropertyAddressInformationsAssembler;
import org.RealEstateMM.services.dtos.property.PropertyInformations;
import org.RealEstateMM.services.dtos.property.PropertyInformationsAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

import com.google.gson.Gson;

public class PropertyService {

	private PropertyRepository propertyRepository;
	private PropertyInformationsAssembler propertyAssembler;

	public PropertyService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyInformationsAssembler();
	}

	public PropertyService(PropertyRepository propertyRepository, PropertyInformationsAssembler propertyAssembler,
			PropertyAddressInformationsAssembler addressAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
	}

	public void uploadProperty(PropertyInformations propertyInfos) {
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
