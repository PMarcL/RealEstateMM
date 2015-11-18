package org.RealEstateMM.domain.property.filters;

public class PropertyFilterFactory {

	public PropertiesSoldThisYearFilter createPropertiesSoldThisYearFilter() {
		return new PropertiesSoldThisYearFilter();
	}

	public PropertyStatusFilter createPropertyStatusFilter() {
		return new PropertyStatusFilter();
	}

}
