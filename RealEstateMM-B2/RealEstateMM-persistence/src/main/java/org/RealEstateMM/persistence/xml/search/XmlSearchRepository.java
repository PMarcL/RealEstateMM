package org.RealEstateMM.persistence.xml.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.RealEstateMM.domain.property.search.Search;
import org.RealEstateMM.domain.property.search.SearchRepository;
import org.RealEstateMM.persistence.xml.EmptyXmlFileException;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlMarshallingException;

public class XmlSearchRepository implements SearchRepository {

	private XmlMarshaller marshaller;
	private XmlSearchAssembler searchAssembler;
	private XmlSearchCollection searchCache;
	
	public XmlSearchRepository(XmlMarshaller marshaller, XmlSearchAssembler searchAssembler){
		this.marshaller = marshaller;
		this.searchAssembler = searchAssembler;
		
		loadSearches();
	}

	private void loadSearches() {
		try {
			searchCache = marshaller.unmarshal(XmlSearchCollection.class);
		} catch (EmptyXmlFileException ex) {
			searchCache = new XmlSearchCollection();
		} catch (XmlMarshallingException e) {
			searchCache = new XmlSearchCollection();
		}
	}
	
	@Override
	public void add(Search newSearch) {
		XmlSearch xmlSearch = searchAssembler.fromSearch(newSearch);
		searchCache.add(xmlSearch);
		marshaller.marshal(XmlSearchCollection.class, searchCache);
	}
	
	@Override
	public void updateSearch(Search search){
		searchCache.removeSearchWithName(search.getSearchName(), search.getUserPseudonyme());
		XmlSearch xmlSearch = searchAssembler.fromSearch(search);
		searchCache.add(xmlSearch);
		marshaller.marshal(XmlSearchCollection.class, searchCache);
	}

	@Override
	public ArrayList<Search> getSearchesOfUser(String userPseudonyme) {
		ArrayList<Search> allSearches = getAll();
		ArrayList<Search> userSearches = new ArrayList<Search>();
		
		for(Search search : allSearches){
			if (search.getUserPseudonyme().equals(userPseudonyme)){
				userSearches.add(search);
			}
		}
		return userSearches;
	}
	
	@Override
	public Optional<Search> getSearchWithName(String searchName) {
		ArrayList<Search> searches = getAll();

		for (Search search : searches) {
			if (search.getSearchName().equals(searchName)) {
				return Optional.of(search);
			}
		}
		return Optional.empty();
	}
	
	@Override
	public ArrayList<Search> getAll() {
		ArrayList<Search> searches = new ArrayList<Search>();
		List<XmlSearch> xmlSearches = searchCache.getSearches();

		for (XmlSearch xmlSearch : xmlSearches) {
			searches.add(searchAssembler.toSearch(xmlSearch));
		}
		return searches;
	}

	
	
	
	
	
	
	
	
}
