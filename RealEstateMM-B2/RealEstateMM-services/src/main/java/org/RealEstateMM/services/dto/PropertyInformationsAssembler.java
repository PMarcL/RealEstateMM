package org.RealEstateMM.services.dto;

import org.RealEstateMM.domain.property.Property;

public class PropertyInformationsAssembler {
	
	public PropertyInformations toDTO(Property property){
		PropertyInformations dto = new PropertyInformations();
		
		dto.setPropertyType(property.propertyType);
		dto.setPropertyAddress(property.propertyAddress);
		dto.setPropertyPrice(property.propertyPrice);
		dto.setPropertyOwner(property.propertyOwner);
		dto.setPropertyStatus(property.propertyStatus);
		return dto;
	}
	
	public Property fromDTO(PropertyInformations propertyInfos){
		return new Property(propertyInfos.getPropertyType(), 
							propertyInfos.getPropertyAddress(), 
							propertyInfos.getPropertyPrice(),
							propertyInfos.getPropertyOwner(), 
							propertyInfos.getPropertyStatus());
	}

}
