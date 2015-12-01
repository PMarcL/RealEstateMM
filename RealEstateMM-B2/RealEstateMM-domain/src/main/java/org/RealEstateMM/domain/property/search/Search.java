package org.RealEstateMM.domain.property.search;

import org.RealEstateMM.domain.user.User;

public class Search {


	private String userPseudonyme;
	private String searchName;
	
	private int roomNumber;
	private int bathroomNumber;
	private int bedroomNumber;
	private double price;
	private String propertyType;
	private String orientation;
	
	
	public Search(String ownerPseudonyme, String searchName, int roomNumber, int bathroomNumber, int bedroomNumber, double price,
			String propertyType, String orientation) {
		this.userPseudonyme = ownerPseudonyme;
		this.searchName = searchName;
		this.roomNumber = roomNumber;
		this.bathroomNumber = bathroomNumber;
		this.bedroomNumber = bedroomNumber;
		this.price = price;
		this.propertyType = propertyType;
		this.orientation = orientation;
	}
	
	public String getUserPseudonyme() {
		return userPseudonyme;
	}
	
	public void setUserPseudonyme(String ownerPseudonyme) {
		this.userPseudonyme = ownerPseudonyme;
	}
	
	public String getSearchName() {
		return searchName;
	}
	
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public int getBathroomNumber() {
		return bathroomNumber;
	}
	
	public void setBathroomNumber(int bathroomNumber) {
		this.bathroomNumber = bathroomNumber;
	}
	
	public int getBedroomNumber() {
		return bedroomNumber;
	}
	
	public void setBedroomNumber(int bedroomNumber) {
		this.bedroomNumber = bedroomNumber;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getPropertyType() {
		return propertyType;
	}
	
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}
