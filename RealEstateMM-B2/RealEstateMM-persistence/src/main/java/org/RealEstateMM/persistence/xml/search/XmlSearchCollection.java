package org.RealEstateMM.persistence.xml.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "searches")
public class XmlSearchCollection {

	private List<XmlSearchDescription> searches;

	public XmlSearchCollection() {
		searches = new ArrayList<>();
	}

	public List<XmlSearchDescription> getSearchesForUser(String pseudonym) {
		return searches.stream().filter(s -> s.getPseudonym().equals(pseudonym)).collect(Collectors.toList());
	}

	public boolean contains(String pseudonym, String searchName) {
		return searches.stream().anyMatch(s -> s.getName().equals(searchName) && s.getPseudonym().equals(pseudonym));
	}

	public void add(XmlSearchDescription xmlSearch) {
		searches.add(xmlSearch);
	}

	public void remove(String pseudonym, String searchName) {
		List<XmlSearchDescription> userSearches = getSearchesForUser(pseudonym);
		Optional<XmlSearchDescription> search = userSearches.stream().filter(s -> s.getName().equals(searchName))
				.findFirst();
		if (search.isPresent()) {
			searches.remove(search.get());
		}
	}

	public List<XmlSearchDescription> getSearches() {
		return searches;
	}

	@XmlElement(name = "search")
	public void setSearches(List<XmlSearchDescription> searches) {
		this.searches = searches;
	}

}
