package org.RealEstateMM.domain.property.informations;

public class PropertyFeatures {
	
	public int numberOfBathrooms;
	public int numberOfBedrooms;
	public int totalNumberOfRooms;
	public int numberOfLevel;
	public double lotDimension;
	public int yearOfConstruction;
	public double livingSpaceArea;
	public String backyardDirection;
	public String description;
	
	public PropertyFeatures(int numberOfBathroom, int numberOfBedroom, int totalNumberOfRooms,  int numberOfLevel,
			double lotDimension, int yearOfConstruction, double livingSpaceArea, String backYardDirection, String description){
		this.numberOfBathrooms = numberOfBathroom;
		this.numberOfBedrooms = numberOfBedroom;
		this.totalNumberOfRooms = totalNumberOfRooms;
		this.numberOfLevel = numberOfLevel;
		this.lotDimension = lotDimension;
		this.yearOfConstruction = yearOfConstruction;
		this.livingSpaceArea = livingSpaceArea;
		this.backyardDirection = backYardDirection;
		this.description = description;
	}
}
