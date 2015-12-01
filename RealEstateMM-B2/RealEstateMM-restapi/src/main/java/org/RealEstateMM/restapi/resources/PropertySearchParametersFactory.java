package org.RealEstateMM.restapi.resources;

import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.search.InvalidSearchParameterException;

public class PropertySearchParametersFactory {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";

	public PropertySearchParametersDTO getSearchParametersDTO(UriInfo searchParam)
			throws InvalidSearchParameterException {
		PropertySearchParametersDTO searchParamDTO = new PropertySearchParametersDTO();

		setOrderingParameter(searchParam, searchParamDTO);
		setMinNumBedroomsParameter(searchParam, searchParamDTO);
		searchParamDTO.setPropertyTypes(searchParam.getQueryParameters().get(PROPERTY_TYPES));

		return searchParamDTO;
	}

	private void setOrderingParameter(UriInfo searchParam, PropertySearchParametersDTO searchParamDTO) {
		String orderBy = searchParam.getQueryParameters().getFirst(ORDER_BY);
		if (orderBy == null) {
			searchParamDTO.setOrderBy(PropertyOrderingParameters.NO_ORDERING.toString());
		} else {
			searchParamDTO.setOrderBy(orderBy);
		}
	}

	private void setMinNumBedroomsParameter(UriInfo searchParam, PropertySearchParametersDTO searchParamDTO)
			throws InvalidSearchParameterException {
		String minNumBedrooms = searchParam.getQueryParameters().getFirst(MIN_NUM_BEDROOMS);
		if (minNumBedrooms != null) {
			try {
				searchParamDTO.setMinNumBedrooms(Integer.parseInt(minNumBedrooms));
			} catch (NumberFormatException e) {
				throw new InvalidSearchParameterException();
			}
		} else {
			searchParamDTO.setMinNumBedrooms(0);
		}
	}
}
