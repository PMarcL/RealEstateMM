package org.RealEstateMM.services.dtos.property;

public class PropertyAddressInformations {

	private String streetNumber;
	private String streetName;
	private String city;
	private String province;
	private String zipCode;
	
	public PropertyAddressInformations(){
		
	}
	
	public PropertyAddressInformations(String streetNumber, String streetName, String city, String province, String zipCode){
		this.setStreetNumber(streetNumber);
		this.setStreetName(streetName);
		this.setCity(city);
		this.setProvince(province);
		this.setZipCode(zipCode);
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
