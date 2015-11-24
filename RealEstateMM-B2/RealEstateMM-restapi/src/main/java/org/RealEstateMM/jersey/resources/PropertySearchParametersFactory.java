package org.RealEstateMM.jersey.resources;

import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;

public class PropertySearchParametersFactory {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";

	public PropertySearchParametersDTO getSearchParametersDTO(UriInfo searchParam) {
		PropertySearchParametersDTO searchParamDTO = new PropertySearchParametersDTO();
		searchParamDTO.setOrderBy(searchParam.getQueryParameters().getFirst(ORDER_BY));
		searchParamDTO.setPropertyTypes(searchParam.getQueryParameters().get(PROPERTY_TYPES));
		String minNumBedrooms = searchParam.getQueryParameters().getFirst(MIN_NUM_BEDROOMS);
		searchParamDTO.setMinNumBedrooms(Integer.parseInt(minNumBedrooms));
		return searchParamDTO;
	}
}
