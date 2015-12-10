package org.RealEstateMM.persistence.xml.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.criterias.SearchCriteria;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingType;
import org.RealEstateMM.persistence.xml.user.XmlSearchCriteria;

public class XmlSearchAssembler {

	private XmlSearchCriteriaAssembler criteriaAssembler;

	public XmlSearchAssembler(XmlSearchCriteriaAssembler criteriaAssembler) {
		this.criteriaAssembler = criteriaAssembler;
	}

	public SearchDescription toSearchDescription(XmlSearchDescription xmlSearch) {
		String name = xmlSearch.getName();
		PropertyOrderingType orderBy = PropertyOrderingType.valueOf(xmlSearch.getOrderBy());
		List<SearchCriteria> criterias = new ArrayList<>();
		xmlSearch.getSearchCriterias().stream()
				.forEach(s -> criterias.add(criteriaAssembler.fromXmlSearchCriterias(s)));
		return new SearchDescription(name, orderBy, criterias);
	}

	public XmlSearchDescription fromSearchDescription(SearchDescription searchDescription, String pseudonym) {
		XmlSearchDescription result = new XmlSearchDescription();
		result.setPseudonym(pseudonym);
		result.setName(searchDescription.getName());
		result.setOrderBy(searchDescription.getOrderBy().toString());
		result.setSearchCriterias(createSearchCriterias(searchDescription.getCriterias()));
		return result;
	}

	private List<XmlSearchCriteria> createSearchCriterias(List<SearchCriteria> criterias) {
		ArrayList<XmlSearchCriteria> result = new ArrayList<>();
		for (SearchCriteria current : criterias) {
			result.add(criteriaAssembler.toXmlSearchCriteria(current));
		}

		return result;
	}

}
