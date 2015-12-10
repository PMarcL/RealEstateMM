package org.RealEstateMM.persistence.xml.search;

import java.util.List;

import org.RealEstateMM.persistence.xml.user.XmlSearchCriteria;

public class XmlSearchDescription {

	private String name;
	private String orderBy;
	private String pseudonym;
	private List<XmlSearchCriteria> criterias;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<XmlSearchCriteria> getSearchCriterias() {
		return criterias;
	}

	public void setSearchCriterias(List<XmlSearchCriteria> criterias) {
		this.criterias = criterias;
	}

}
