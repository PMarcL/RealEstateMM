package org.RealEstateMM.persistence.xml.search;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "search")
public class XmlSearchDescription {
	private String pseudonym;
	private String name;
	private String orderBy;
	private List<String> propertyTypes;
	private int minNumBedrooms;
	private int minNumBathrooms;
	private int minPrice;
	private int maxPrice;

	public XmlSearchDescription() {

	}

	public String getOrderBy() {
		return orderBy;
	}

	@XmlElement(name = "orderBy")
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<String> getPropertyTypes() {
		return propertyTypes;
	}

	@XmlElement(name = "propertyTypes")
	public void setPropertyTypes(List<String> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	public int getMinNumBedrooms() {
		return minNumBedrooms;
	}

	@XmlElement(name = "minNumBedrooms")
	public void setMinNumBedrooms(int minNumBedrooms) {
		this.minNumBedrooms = minNumBedrooms;
	}

	public int getMinNumBathrooms() {
		return minNumBathrooms;
	}

	@XmlElement(name = "minNumBathrooms")
	public void setMinNumBathrooms(int minNumBathrooms) {
		this.minNumBathrooms = minNumBathrooms;
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public int getMinPrice() {
		return minPrice;
	}

	@XmlElement(name = "minPrice")
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	@XmlElement(name = "maxPrice")
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	@XmlElement(name = "pseudonym")
	public void setPseudonym(String pseudonyme) {
		this.pseudonym = pseudonyme;
	}
}
