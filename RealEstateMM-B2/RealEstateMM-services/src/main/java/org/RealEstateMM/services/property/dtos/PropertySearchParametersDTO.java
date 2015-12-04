package org.RealEstateMM.services.property.dtos;

import java.util.List;

public class PropertySearchParametersDTO {
	private String orderBy;
	private List<String> propertyTypes;
	private int minNumBedrooms;
	private int minNumBathrooms;

	public PropertySearchParametersDTO() {

	}

	public PropertySearchParametersDTO(String orderBy, List<String> propertyTypes, int minNumBedrooms,
			int minNumBathrooms) {
		setOrderBy(orderBy);
		setPropertyTypes(propertyTypes);
		setMinNumBedrooms(minNumBedrooms);
		setMinNumBathrooms(minNumBathrooms);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<String> getPropertyTypes() {
		return propertyTypes;
	}

	public void setPropertyTypes(List<String> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	public int getMinNumBedrooms() {
		return minNumBedrooms;
	}

	public void setMinNumBedrooms(int minNumBedrooms) {
		this.minNumBedrooms = minNumBedrooms;
	}

	public int getMinNumBathrooms() {
		return minNumBathrooms;
	}

	public void setMinNumBathrooms(int minNumBathrooms) {
		this.minNumBathrooms = minNumBathrooms;
	}
}
