package org.RealEstateMM.services.property.dtos;

import java.util.List;

public class PropertySearchParametersDTO {
	private String orderBy;
	private List<String> propertyTypes;
	private int minNumBedrooms;

	public PropertySearchParametersDTO() {

	}

	public PropertySearchParametersDTO(String orderBy, List<String> propertyTypes, int minNumBedrooms) {
		setOrderBy(orderBy);
		setPropertyTypes(propertyTypes);
		setMinNumBedrooms(minNumBedrooms);
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
}
