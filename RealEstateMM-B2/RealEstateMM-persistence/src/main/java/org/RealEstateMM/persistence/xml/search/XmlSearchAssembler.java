package org.RealEstateMM.persistence.xml.search;

import org.RealEstateMM.domain.search.SearchDTO;

public class XmlSearchAssembler {

	public SearchDTO toSearchDTO(XmlSearchDescription xmlSearch) {
		SearchDTO searchDto = new SearchDTO();

		searchDto.setName(xmlSearch.getName());
		searchDto.setOrderBy(xmlSearch.getOrderBy());
		searchDto.setMaxPrice(xmlSearch.getMaxPrice());
		searchDto.setMinPrice(xmlSearch.getMinPrice());
		searchDto.setMinNumBathrooms(xmlSearch.getMinNumBathrooms());
		searchDto.setMinNumBedrooms(xmlSearch.getMinNumBedrooms());
		searchDto.setPropertyTypes(xmlSearch.getPropertyTypes());

		return searchDto;
	}

	public XmlSearchDescription fromSearchDTO(SearchDTO searchDescription, String pseudonym) {
		XmlSearchDescription result = new XmlSearchDescription();

		result.setPseudonym(pseudonym);
		result.setName(searchDescription.getName());
		result.setOrderBy(searchDescription.getOrderBy());
		result.setMaxPrice(searchDescription.getMaxPrice());
		result.setMinNumBathrooms(searchDescription.getMinNumBathrooms());
		result.setMinNumBedrooms(searchDescription.getMinNumBedrooms());
		result.setMinPrice(searchDescription.getMinPrice());
		result.setPropertyTypes(searchDescription.getPropertyTypes());

		return result;
	}

}
