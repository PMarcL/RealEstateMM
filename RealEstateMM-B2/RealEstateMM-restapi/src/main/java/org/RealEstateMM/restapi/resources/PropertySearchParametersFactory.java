package org.RealEstateMM.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.domain.search.PropertyOrderingParameters;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.search.InvalidSearchParameterException;

public class PropertySearchParametersFactory {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";
	private final String MIN_NUM_BATHROOMS = "minNumBathrooms";

	public PropertySearchParametersDTO getSearchParametersDTO(UriInfo searchParam)
			throws InvalidSearchParameterException {
		PropertySearchParametersDTO searchParamDTO = new PropertySearchParametersDTO();

		setOrderingParameter(searchParam, searchParamDTO);
		setMinNumBedroomsParameter(searchParam, searchParamDTO);
		setMinNumBathroomsParameter(searchParam, searchParamDTO);
		setPropertyTypesToFilter(searchParam, searchParamDTO);

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

	private void setMinNumBathroomsParameter(UriInfo searchParam, PropertySearchParametersDTO searchParamDTO)
			throws InvalidSearchParameterException {
		String minNumBathrooms = searchParam.getQueryParameters().getFirst(MIN_NUM_BATHROOMS);
		if (minNumBathrooms != null) {
			try {
				searchParamDTO.setMinNumBathrooms(Integer.parseInt(minNumBathrooms));
			} catch (NumberFormatException e) {
				throw new InvalidSearchParameterException();
			}
		} else {
			searchParamDTO.setMinNumBathrooms(0);
		}
	}

	private void setPropertyTypesToFilter(UriInfo searchParam, PropertySearchParametersDTO searchParamDTO) {
		List<String> propertyTypesToFilter = searchParam.getQueryParameters().get(PROPERTY_TYPES);
		if (propertyTypesToFilter != null) {
			searchParamDTO.setPropertyTypes(propertyTypesToFilter);
		} else {
			searchParamDTO.setPropertyTypes(new ArrayList<String>());
		}
	}
}
