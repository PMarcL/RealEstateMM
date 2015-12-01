package org.RealEstateMM.persistence.xml.search;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.RealEstateMM.persistence.xml.property.XmlProperty;

@XmlRootElement(name = "searches")
public class XmlSearchCollection {
	
	private List<XmlSearch> searches;
	
	public XmlSearchCollection(){
		searches = new ArrayList<XmlSearch>();
	}

	public void add(XmlSearch newSearch) {
		searches.add(newSearch);
	}

	public List<XmlSearch> getSearches(){
		return searches;
	}

	public void removeSearchWithName(String searchName, String ownerPseudonyme) {
		Optional<XmlSearch> search = find(searchName, ownerPseudonyme);
		searches.remove(search.get());
	}

	private Optional<XmlSearch> find(String searchName, String ownerPseudonyme) {
		for (XmlSearch search : searches) {
			if (search.getSearchName().equals(searchName) && search.getUserPseudonyme().equals(ownerPseudonyme)) {
				return Optional.of(search);
			}
		}
		return Optional.empty();
	}
}
