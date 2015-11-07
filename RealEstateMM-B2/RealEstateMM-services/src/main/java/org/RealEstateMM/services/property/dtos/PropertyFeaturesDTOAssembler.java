package org.RealEstateMM.services.property.dtos;

import org.RealEstateMM.domain.property.informations.PropertyFeatures;

public class PropertyFeaturesDTOAssembler {

	public PropertyFeatures fromDTO(PropertyFeaturesDTO dto) {
		return new PropertyFeatures(dto.getNumberOfBathrooms(),dto.getNumberOfBedrooms(), dto.getTotalNumberOfRooms(),
				dto.getNumberOfLevels(),dto.getLotDimensions(), dto.getYearOfConstruction(),dto.getLivingSpaceArea(), dto.getBackyardDirection(), dto.getDescription());
	}

	public PropertyFeaturesDTO toDTO(PropertyFeatures features) {
		PropertyFeaturesDTO dto = new PropertyFeaturesDTO();
		
		dto.setNumberOfBathrooms(features.numberOfBathrooms);
		dto.setNumberOfBedrooms(features.numberOfBedrooms);
		dto.setTotalNumberOfRooms(features.totalNumberOfRooms);
		dto.setNumberOfLevels(features.numberOfLevel);
		dto.setLotDimensions(features.lotDimension);
		dto.setYearOfConstruction(features.yearOfConstruction);
		dto.setLivingSpaceArea(features.livingSpaceArea);
		dto.setBackyardDirection(features.backyardDirection);
		dto.setDescription(features.description);
		
		return dto;
	}

}
