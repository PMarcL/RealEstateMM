package org.RealEstateMM.services.dtos.property;

public class PropertyFeaturesDTO {
	private int numberOfBathrooms;
	private int numberOfBedrooms;
	private int totalNumberOfRooms;
	private int numberOfLevels;
	private double lotDimensions;
	private int yearOfConstruction;
	private double livingSpaceArea;
	private String backyardDirection;
	private String description;

	public PropertyFeaturesDTO() {
	}

	public PropertyFeaturesDTO(int numberOfBathrooms, int numberOfBedrooms, int totalNumberOfRooms, int numberOfLevels,
			double lotDimension, int yearOfConstruction, double livingSpaceArea, String backyardDirection,
			String description) {
		setNumberOfBathrooms(numberOfBathrooms);
		setNumberOfBedrooms(numberOfBedrooms);
		setTotalNumberOfRooms(totalNumberOfRooms);
		setNumberOfLevels(numberOfLevels);
		setLotDimensions(lotDimension);
		setYearOfConstruction(yearOfConstruction);
		setLivingSpaceArea(livingSpaceArea);
		setBackyardDirection(backyardDirection);
		setDescription(description);
	}

	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}

	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}

	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public int getTotalNumberOfRooms() {
		return totalNumberOfRooms;
	}

	public void setTotalNumberOfRooms(int totalNumberOfRooms) {
		this.totalNumberOfRooms = totalNumberOfRooms;
	}

	public int getNumberOfLevels() {
		return numberOfLevels;
	}

	public void setNumberOfLevels(int numberOfLevels) {
		this.numberOfLevels = numberOfLevels;
	}

	public double getLotDimensions() {
		return lotDimensions;
	}

	public void setLotDimensions(double lotDimensions) {
		this.lotDimensions = lotDimensions;
	}

	public int getYearOfConstruction() {
		return yearOfConstruction;
	}

	public void setYearOfConstruction(int yearOfConstruction) {
		this.yearOfConstruction = yearOfConstruction;
	}

	public double getLivingSpaceArea() {
		return livingSpaceArea;
	}

	public void setLivingSpaceArea(double livingSpaceArea) {
		this.livingSpaceArea = livingSpaceArea;
	}

	public String getBackyardDirection() {
		return backyardDirection;
	}

	public void setBackyardDirection(String backyardDirection) {
		this.backyardDirection = backyardDirection;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
