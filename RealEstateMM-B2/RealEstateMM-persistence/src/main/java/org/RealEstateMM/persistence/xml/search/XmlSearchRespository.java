package org.RealEstateMM.persistence.xml.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.search.SearchDTO;
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
	public void addSearch(SearchDTO searchDTO, String pseudonym) {
		XmlSearchDescription xmlSearch = assembler.fromSearchDTO(searchDTO, pseudonym);

		String searchName = searchDTO.getName();
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
	public List<SearchDTO> getSearchesForUser(String pseudonym) {
		List<XmlSearchDescription> xmlSearches = searchCache.getSearchesForUser(pseudonym);
		List<SearchDTO> result = new ArrayList<>();
		xmlSearches.stream().forEach(s -> result.add(assembler.toSearchDTO(s)));
		return result;
	}

	@Override
	public void remove(String pseudonym, String searchName) {
		searchCache.remove(pseudonym, searchName);
		marshalSearches();
	}

	@Override
	public SearchDTO getSearchWithNameForUser(String pseudonym, String searchName) throws SearchNotFoundException {
		List<XmlSearchDescription> searches = searchCache.getSearchesForUser(pseudonym);
		Optional<XmlSearchDescription> xmlSearch = searches.stream().filter(s -> s.getName() == searchName).findFirst();
		if (xmlSearch.isPresent()) {
			return assembler.toSearchDTO(xmlSearch.get());
		} else {
			throw new SearchNotFoundException();
		}
	}

}
