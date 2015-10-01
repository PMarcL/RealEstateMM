package org.RealEstateMM.persistence.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class XmlProperty {

	private String type;
	private String price;
	private String ownerUserName;
	private String status;
	
	private String streetAddress;
	private String cityAddress;
	private String provinceAddress;
	private String zipCodeAddress;
	
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
	
	@XmlElement(name = "firstName")
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

	public String getSteetAddress() {
		return streetAddress;
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
	
}
