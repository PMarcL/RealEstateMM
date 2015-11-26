package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.informations.PropertyType;

public class PropertySearchParameters {
	private PropertyOrderingParameters orderingParam;
	private ArrayList<PropertyType> propertyTypesToFilter;
	private int minNumberOfBedrooms;

	public PropertySearchParameters(PropertyOrderingParameters orderingParam,
			ArrayList<PropertyType> propertyTypesToFilter, int minNumberOfBedrooms) {
		setOrderingParam(orderingParam);
		setPropertyTypesToFilter(propertyTypesToFilter);
		setMinNumberOfBedrooms(minNumberOfBedrooms);
	}

	public PropertyOrderingParameters getOrderingParam() {
		return orderingParam;
	}

	public void setOrderingParam(PropertyOrderingParameters orderingParam) {
		this.orderingParam = orderingParam;
	}

	public ArrayList<PropertyType> getPropertyTypesToFilter() {
		return propertyTypesToFilter;
	}

	public void setPropertyTypesToFilter(ArrayList<PropertyType> propertyTypesToFilter) {
		this.propertyTypesToFilter = propertyTypesToFilter;
	}

	public int getMinNumberOfBedrooms() {
		return minNumberOfBedrooms;
	}

	public void setMinNumberOfBedrooms(int minNumberOfBedrooms) {
		this.minNumberOfBedrooms = minNumberOfBedrooms;
	}

	public boolean hasPropertyTypesToFilter() {
		return !propertyTypesToFilter.isEmpty();
	}

	public boolean hasMinNumOfBedrooms() {
		return minNumberOfBedrooms != 0;
	}

}
