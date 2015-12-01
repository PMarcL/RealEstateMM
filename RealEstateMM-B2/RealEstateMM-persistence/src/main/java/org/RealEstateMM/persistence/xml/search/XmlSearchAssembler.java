package org.RealEstateMM.persistence.xml.search;

import org.RealEstateMM.domain.property.search.Search;

public class XmlSearchAssembler {

	public XmlSearch fromSearch(Search search){
		XmlSearch newSearch = new XmlSearch();
		
		newSearch.setBathroomNumber(String.valueOf(search.getBathroomNumber()));
		newSearch.setBedroomNumber(String.valueOf(search.getBedroomNumber()));
		newSearch.setOrientation(search.getOrientation());
		newSearch.setUserPseudonyme(search.getUserPseudonyme());
		newSearch.setPrice(String.valueOf(search.getPrice()));
		newSearch.setPropertyType(search.getPropertyType());
		newSearch.setRoomNumber(String.valueOf(search.getRoomNumber()));
		newSearch.setSearchName(search.getSearchName());
		
		return newSearch;
	}
	
	public Search toSearch(XmlSearch xmlSearch){
		Search search = new Search(xmlSearch.getUserPseudonyme(), xmlSearch.getSearchName(), Integer.parseInt(xmlSearch.getRoomNumber()), 
				Integer.parseInt(xmlSearch.getBathroomNumber()), Integer.parseInt(xmlSearch.getBedroomNumber()), Double.parseDouble(xmlSearch.getPrice()), 
				xmlSearch.getPropertyType(), xmlSearch.getOrientation());
		
		return search;
	}
}
