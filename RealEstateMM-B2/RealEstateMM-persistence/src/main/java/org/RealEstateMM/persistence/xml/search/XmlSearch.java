package org.RealEstateMM.persistence.xml.search;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "search")
public class XmlSearch {

	private String userPseudonyme;
	private String searchName;
	
	private String roomNumber;
	private String bathroomNumber;
	private String bedroomNumber;
	private String price;
	private String propertyType;
	private String orientation;
	
	
	public String getUserPseudonyme() {
		return userPseudonyme;
	}

	@XmlElement(name = "ownerPseudonyme")
	public void setUserPseudonyme(String ownerPseudonyme) {
		this.userPseudonyme = ownerPseudonyme;
	}

	public String getSearchName() {
		return searchName;
	}
	
	@XmlElement(name = "searchName")
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}
	
	@XmlElement(name = "roomNumber")
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String getBathroomNumber() {
		return bathroomNumber;
	}
	
	@XmlElement(name = "bathroomNumber")
	public void setBathroomNumber(String bathroomNumber) {
		this.bathroomNumber = bathroomNumber;
	}
	
	public String getBedroomNumber() {
		return bedroomNumber;
	}
	
	@XmlElement(name = "bedroomNumber")
	public void setBedroomNumber(String bedroomNumber) {
		this.bedroomNumber = bedroomNumber;
	}
	
	public String getPrice() {
		return price;
	}
	
	@XmlElement(name = "price")
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getPropertyType() {
		return propertyType;
	}
	
	@XmlElement(name = "propertyType")
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	@XmlElement(name = "orientation")
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}
