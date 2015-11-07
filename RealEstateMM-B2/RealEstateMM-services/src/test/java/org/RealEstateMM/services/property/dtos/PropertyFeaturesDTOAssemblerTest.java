package org.RealEstateMM.services.property.dtos;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.property.informations.PropertyFeatures;
import org.RealEstateMM.services.property.dtos.PropertyFeaturesDTO;
import org.RealEstateMM.services.property.dtos.PropertyFeaturesDTOAssembler;
import org.junit.Before;
import org.junit.Test;

public class PropertyFeaturesDTOAssemblerTest {

	private PropertyFeaturesDTOAssembler assembler;
	
	private PropertyFeatures features;
	private PropertyFeaturesDTO featuresDTO;
	
	private int A_NUMBER_OF_BATHROOM = 2;
	private int A_NUMBER_OF_BEDROOMS = 2;
	private int A_TOTAL_NUMBER_OF_ROOMS = 5;
	private int A_NUMBER_OF_LEVELS = 2;
	private double A_LOT_DIMENSION = 200.00;
	private int A_YEAR_OF_CONSTRUCTION = 1999;
	private double A_LIVING_SPACE_AREA = 100.00;
	private String A_BACKYARD_DIRECTION = "West";
	private String A_DESCRIPTION = "Not a good place to live";
	
	private double DELTA = 0.001;
	
	@Before
	public void setup(){
		assembler = new PropertyFeaturesDTOAssembler();
		features = new PropertyFeatures(A_NUMBER_OF_BATHROOM, A_NUMBER_OF_BEDROOMS, A_TOTAL_NUMBER_OF_ROOMS, 
				A_NUMBER_OF_LEVELS, A_LOT_DIMENSION, A_YEAR_OF_CONSTRUCTION, A_LIVING_SPACE_AREA, A_BACKYARD_DIRECTION, A_DESCRIPTION);
	}
	
	@Test
	public void givenAPropertyFeaturesDTOWhenBuildingAPropertyFeatureThenTheReturnedPropertyFeatureShouldHaveTheSameInformations(){
		featuresDTO = assembler.toDTO(features);
		
		assertEquals(features.numberOfBathrooms, featuresDTO.getNumberOfBathrooms());
		assertEquals(features.numberOfBedrooms, featuresDTO.getNumberOfBedrooms());
		assertEquals(features.totalNumberOfRooms, featuresDTO.getTotalNumberOfRooms());
		assertEquals(features.numberOfLevel, featuresDTO.getNumberOfLevels());
		assertEquals(features.lotDimension, featuresDTO.getLotDimensions(), DELTA);
		assertEquals(features.yearOfConstruction, featuresDTO.getYearOfConstruction());
		assertEquals(features.livingSpaceArea, featuresDTO.getLivingSpaceArea(), DELTA);
		assertEquals(features.backyardDirection, featuresDTO.getBackyardDirection());
	}
	
	@Test
	public void givenAPropertyFeatureDTOWhenAssembledThenShouldReturnedAPropertyFeatureWithTheSameInformations(){
		featuresDTO = assembler.toDTO(features);
		PropertyFeatures result = assembler.fromDTO(featuresDTO);
		
		assertEquals(featuresDTO.getNumberOfBathrooms(), result.numberOfBathrooms);
		assertEquals(featuresDTO.getNumberOfBedrooms(), result.numberOfBedrooms);
		assertEquals(featuresDTO.getTotalNumberOfRooms(), result.totalNumberOfRooms);
		assertEquals(featuresDTO.getNumberOfLevels(), result.numberOfLevel);
		assertEquals(featuresDTO.getLotDimensions(), result.lotDimension, DELTA);
		assertEquals(featuresDTO.getYearOfConstruction(), result.yearOfConstruction);
		assertEquals(featuresDTO.getLivingSpaceArea(), result.livingSpaceArea, DELTA);
		assertEquals(featuresDTO.getBackyardDirection(), result.backyardDirection);
	}
}
