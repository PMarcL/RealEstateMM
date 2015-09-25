package org.RealEstateMM.services;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.services.dto.PropertyAddressInformations;
import org.RealEstateMM.services.dto.PropertyAddressInformationsAssembler;
import org.RealEstateMM.services.dto.PropertyInformations;
import org.RealEstateMM.services.dto.PropertyInformationsAssembler;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class PropertyUploadService { //TODO Change class name
	
	private PropertyRepository propertyRepository;
	private PropertyInformationsAssembler propertyAssembler;
	private PropertyAddressInformationsAssembler addressAssembler;

	public PropertyUploadService() {
		propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		propertyAssembler = new PropertyInformationsAssembler();
		addressAssembler  = new PropertyAddressInformationsAssembler();
	}

	public PropertyUploadService(PropertyRepository propertyRepository, PropertyInformationsAssembler propertyAssembler, PropertyAddressInformationsAssembler addressAssembler) {
		this.propertyRepository = propertyRepository;
		this.propertyAssembler = propertyAssembler;
		this.addressAssembler = addressAssembler;
	}
	
	public void uploadProperty(PropertyInformations propertyInfos, PropertyAddressInformations addressInfo){
		PropertyAddress newAddress = addressAssembler.fromDTO(addressInfo);
		propertyInfos.setPropertyAddress(newAddress); //TODO J'ai vraiment pas le feeling que c'est une bonne pratique
													  //d'injecter ça tout de suite avant d'aller le chercher...
													  //Aussi, il faudrait que je fasse la même chose pour le User dans le constructeur de la propriété...
		Property newProperty = propertyAssembler.fromDTO(propertyInfos);
		propertyRepository.add(newProperty);
	}
	
	public void editPropertyInfos(Property property){
		//TODO Implement
	}
}
