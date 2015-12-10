package org.RealEstateMM.persistence.xml.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.search.SearchDescription;
import org.RealEstateMM.domain.search.SearchNotFoundException;
import org.RealEstateMM.domain.search.SearchRepository;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;

public class XmlSearchRespository implements SearchRepository {

	private XmlMarshaller marshaller;
	private XmlSearchAssembler assembler;
	private XmlSearchCollection searchCache;

	public XmlSearchRespository(XmlMarshaller marshaller, XmlSearchAssembler assembler) {
		this.marshaller = marshaller;
		this.assembler = assembler;
		loadSearches();
	}

	private void loadSearches() {
		try {
			searchCache = marshaller.unmarshal(XmlSearchCollection.class);
		} catch (EmptyXmlFileException | XmlMarshallingException e) {
			searchCache = new XmlSearchCollection();
		}
	}

	@Override
	public void addSearch(SearchDescription searchDescription, String pseudonym) {
		XmlSearchDescription xmlSearch = assembler.fromSearchDescription(searchDescription, pseudonym);

		String searchName = searchDescription.getName();
		if (searchCache.contains(pseudonym, searchName)) {
			searchCache.remove(pseudonym, searchName);
		}
		searchCache.add(xmlSearch);

		marshalSearches();
	}

	private void marshalSearches() {
		marshaller.marshal(XmlSearchCollection.class, searchCache);
	}

	@Override
	public List<SearchDescription> getSearchesForUser(String pseudonym) {
		List<XmlSearchDescription> xmlSearches = searchCache.getSearchesForUser(pseudonym);
		List<SearchDescription> result = new ArrayList<>();
		xmlSearches.stream().forEach(s -> result.add(assembler.toSearchDescription(s)));
		return result;
	}

	@Override
	public void remove(String pseudonym, String searchName) {
		searchCache.remove(pseudonym, searchName);
		marshalSearches();
	}

	@Override
	public SearchDescription getSearchWithNameForUser(String pseudonym, String searchName)
			throws SearchNotFoundException {
		List<XmlSearchDescription> searches = searchCache.getSearchesForUser(pseudonym);
		Optional<XmlSearchDescription> xmlSearch = searches.stream().filter(s -> s.getName() == searchName).findFirst();
		if (xmlSearch.isPresent()) {
			return assembler.toSearchDescription(xmlSearch.get());
		} else {
			throw new SearchNotFoundException();
		}
	}

}
