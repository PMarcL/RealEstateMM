package org.RealEstateMM.persistence.xml.property;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class XmlProperty {

	private String type;
	private String price;
	private String ownerUserName;
	private String status;
	private String creationDate;
	private String saleDate;

	private String streetAddress;
	private String cityAddress;
	private String provinceAddress;
	private String zipCodeAddress;

	private String numberOfBathrooms;
	private String numberOfBedrooms;
	private String totalNumberOfRooms;
	private String numberOfLevel;
	private String lotDimension;
	private String yearOfConstruction;
	private String livingSpaceArea;
	private String backyardDirection;
	private String description;

	public String getType() {
		return type;
	}

	@XmlElement(name = "type")
	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	@XmlElement(name = "price")
	public void setPrice(String price) {
		this.price = price;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	@XmlElement(name = "ownerUserName")
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement(name = "status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getSaleDate() {
		return saleDate;
	}

	@XmlElement(name = "creationDate")
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	@XmlElement(name = "creationDate")
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@XmlElement(name = "streetAddress")
	public void setStreetAddress(String steetAddress) {
		this.streetAddress = steetAddress;
	}

	public String getCityAddress() {
		return cityAddress;
	}

	@XmlElement(name = "cityAddress")
	public void setCityAddress(String cityAddress) {
		this.cityAddress = cityAddress;
	}

	public String getProvinceAddress() {
		return provinceAddress;
	}

	@XmlElement(name = "provinceAddress")
	public void setProvinceAddress(String provinceAddress) {
		this.provinceAddress = provinceAddress;
	}

	public String getZipCodeAddress() {
		return zipCodeAddress;
	}

	@XmlElement(name = "zipCodeAddress")
	public void setZipCodeAddress(String zipCodeAddress) {
		this.zipCodeAddress = zipCodeAddress;
	}

	public String getNumberOfBathrooms() {
		return numberOfBathrooms;
	}

	@XmlElement(name = "numberOfBathrooms")
	public void setNumberOfBathrooms(String numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}

	public String getNumberOfBedrooms() {
		return numberOfBedrooms;
	}

	@XmlElement(name = "numberOfBedrooms")
	public void setNumberOfBedrooms(String numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}

	public String getTotalNumberOfRooms() {
		return totalNumberOfRooms;
	}

	@XmlElement(name = "totalNumberOfRooms")
	public void setTotalNumberOfRooms(String totalNumberOfRooms) {
		this.totalNumberOfRooms = totalNumberOfRooms;
	}

	public String getNumberOfLevel() {
		return numberOfLevel;
	}

	@XmlElement(name = "numberOfLevel")
	public void setNumberOfLevel(String numberOfLevel) {
		this.numberOfLevel = numberOfLevel;
	}

	public String getLotDimension() {
		return lotDimension;
	}

	@XmlElement(name = "lotDimension")
	public void setLotDimension(String lotDimension) {
		this.lotDimension = lotDimension;
	}

	public String getYearOfConstruction() {
		return yearOfConstruction;
	}

	@XmlElement(name = "yearOfConstruction")
	public void setYearOfConstruction(String yearOfConstruction) {
		this.yearOfConstruction = yearOfConstruction;
	}

	public String getLivingSpaceArea() {
		return livingSpaceArea;
	}

	@XmlElement(name = "livingSpaceArea")
	public void setLivingSpaceArea(String livingSpaceArea) {
		this.livingSpaceArea = livingSpaceArea;
	}

	public String getBackyardDirection() {
		return backyardDirection;
	}

	@XmlElement(name = "backyardDirection")
	public void setBackyardDirection(String backyardDirection) {
		this.backyardDirection = backyardDirection;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement(name = "description")
	public void setDescription(String description) {
		this.description = description;
	}
}
