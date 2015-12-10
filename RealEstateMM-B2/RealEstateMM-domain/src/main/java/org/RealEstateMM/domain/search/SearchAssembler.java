package org.RealEstateMM.domain.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.informations.PropertyType;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.criterias.SearchCriteriaFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;

public class SearchAssembler {

	private SearchCriteriaFactory criteriaFactory;
	private SearchFactory searchFactory;

	public SearchAssembler(SearchCriteriaFactory criteriaFactory, SearchFactory searchFactory) {
		this.criteriaFactory = criteriaFactory;
		this.searchFactory = searchFactory;
	}

	public Search fromDTO(SearchDTO searchDTO) {
		PropertyOrderingType orderBy = getOrderingType(searchDTO.getOrderBy());
		List<SearchCriteria> criterias = createCriterias(searchDTO);
		return searchFactory.createSearch(orderBy, criterias);
	}

	private List<SearchCriteria> createCriterias(SearchDTO searchDTO) {
		List<SearchCriteria> criterias = new ArrayList<>();
		if (searchDTO.getPropertyTypes() != null) {
			List<PropertyType> propertyTypes = extractPropertyTypes(searchDTO.getPropertyTypes());
			criterias.add(criteriaFactory.createPropertyTypeCriteria(propertyTypes));
		}
		if (searchDTO.getMinNumBedrooms() > 0) {
			criterias.add(criteriaFactory.createMinimumBedroomNumberCriteria(searchDTO.getMinNumBedrooms()));
		}
		if (searchDTO.getMinNumBathrooms() > 0) {
			criterias.add(criteriaFactory.createMinimumBathroomNumberCriteria(searchDTO.getMinNumBathrooms()));
		}
		if (searchDTO.getMaxPrice() > 0) {
			criterias.add(criteriaFactory.createMaximumPriceCriteria(searchDTO.getMaxPrice()));
		}
		if (searchDTO.getMinPrice() > 0) {
			criterias.add(criteriaFactory.createMinimumPriceCriteria(searchDTO.getMinPrice()));
		}
		return criterias;
	}

	private List<PropertyType> extractPropertyTypes(List<String> propertyTypeDescriptions) {
		List<PropertyType> result = new ArrayList<PropertyType>();
		for (String description : propertyTypeDescriptions) {
			PropertyType type;
			try {
				type = PropertyType.valueOf(description.toUpperCase());
			} catch (IllegalArgumentException e) {
				continue;
			}

			result.add(type);
		}
		return result;
	}

	private PropertyOrderingType getOrderingType(String orderBy) {
		try {
			return PropertyOrderingType.valueOf(orderBy.toUpperCase());
		} catch (IllegalArgumentException e) {
			return PropertyOrderingType.NO_ORDERING;
		}
	}
}
