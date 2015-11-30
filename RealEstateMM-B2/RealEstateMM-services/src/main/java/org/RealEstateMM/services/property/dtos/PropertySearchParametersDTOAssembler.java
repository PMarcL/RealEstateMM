package org.RealEstateMM.services.property.dtos;

import java.util.ArrayList;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.property.search.PropertyOrderingParameters;
import org.RealEstateMM.domain.property.search.PropertySearchParameters;
import org.RealEstateMM.services.property.InvalidSearchParameterException;
import org.RealEstateMM.services.property.PropertyOrderingParametersParser;

public class PropertySearchParametersDTOAssembler {

	private PropertyOrderingParametersParser orderingParamParser;

	public PropertySearchParametersDTOAssembler(PropertyOrderingParametersParser orderingParamParser) {
		this.orderingParamParser = orderingParamParser;
	}

	public PropertySearchParameters fromDTO(PropertySearchParametersDTO searchParamDTO)
			throws InvalidSearchParameterException {
		PropertyOrderingParameters oderBy = orderingParamParser.getParsedSearchParameter(searchParamDTO.getOrderBy());
		int minNumBedrooms = searchParamDTO.getMinNumBedrooms();
		ArrayList<PropertyType> propertyTypesToFilter = buildPropertyTypesToFilterList(searchParamDTO);

		return new PropertySearchParameters(oderBy, propertyTypesToFilter, minNumBedrooms);
	}

	private ArrayList<PropertyType> buildPropertyTypesToFilterList(PropertySearchParametersDTO searchParamDTO) {
		ArrayList<PropertyType> propertyTypesToFilter = new ArrayList<PropertyType>();
		if (searchParamDTO.getPropertyTypes() != null) {
			for (String propertyTypeStr : searchParamDTO.getPropertyTypes()) {
				propertyTypesToFilter.add(PropertyType.getTypeFromString(propertyTypeStr));
			}
		}
		return propertyTypesToFilter;
	}
}
