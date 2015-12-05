package org.RealEstateMM.restapi.resources.queryparser;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.RealEstateMM.domain.search.PropertyOrderingType;
import org.RealEstateMM.services.search.InvalidSearchParameterException;
import org.RealEstateMM.services.search.dtos.SearchDTO;

public class PropertySearchParametersFactory {
	private final String ORDER_BY = "orderBy";
	private final String PROPERTY_TYPES = "propertyTypes";
	private final String MIN_NUM_BEDROOMS = "minNumBedrooms";
	private final String MIN_NUM_BATHROOMS = "minNumBathrooms";

	public SearchDTO getSearchParametersDTO(UriInfo searchParam)
			throws InvalidSearchParameterException {
		SearchDTO searchParamDTO = new SearchDTO();

		setOrderingParameter(searchParam, searchParamDTO);
		setMinNumBedroomsParameter(searchParam, searchParamDTO);
		setMinNumBathroomsParameter(searchParam, searchParamDTO);
		setPropertyTypesToFilter(searchParam, searchParamDTO);

		return searchParamDTO;
	}

	private void setOrderingParameter(UriInfo searchParam, SearchDTO searchParamDTO) {
		String orderBy = searchParam.getQueryParameters().getFirst(ORDER_BY);
		if (orderBy == null) {
			searchParamDTO.setOrderBy(PropertyOrderingType.NO_ORDERING.toString());
		} else {
			searchParamDTO.setOrderBy(orderBy);
		}
	}

	private void setMinNumBedroomsParameter(UriInfo searchParam, SearchDTO searchParamDTO)
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

	private void setMinNumBathroomsParameter(UriInfo searchParam, SearchDTO searchParamDTO)
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

	private void setPropertyTypesToFilter(UriInfo searchParam, SearchDTO searchParamDTO) {
		List<String> propertyTypesToFilter = searchParam.getQueryParameters().get(PROPERTY_TYPES);
		if (propertyTypesToFilter != null) {
			searchParamDTO.setPropertyTypes(propertyTypesToFilter);
		} else {
			searchParamDTO.setPropertyTypes(new ArrayList<String>());
		}
	}
}
