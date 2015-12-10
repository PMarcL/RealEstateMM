package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.search.SearchAssembler;
import org.RealEstateMM.domain.search.SearchEngine;
import org.RealEstateMM.domain.search.SearchFactory;
import org.RealEstateMM.domain.search.SearchRepository;
import org.RealEstateMM.domain.search.Searches;
import org.RealEstateMM.domain.search.criterias.SearchCriteriaFactory;
import org.RealEstateMM.domain.search.ordering.PropertyOrderingStrategyFactory;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.search.SearchService;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.RealEstateMM.services.search.SearchServiceSecurity;
import org.RealEstateMM.services.search.validation.SearchServiceAntiCorruption;

public class SearchServiceFactory {

	public SearchServiceHandler create(PropertyRepository propertyRepository, SearchRepository searchRepository,
			UserRepository userRepository) {
		Searches searches = createSearches(propertyRepository, searchRepository);
		SearchService service = new SearchService(new PropertyAssembler(), searches);
		SearchServiceSecurity searchSecurity = new SearchServiceSecurity(service,
				new UserAuthorizations(userRepository));
		return new SearchServiceAntiCorruption(searchSecurity, new PropertyInformationsValidator());
	}

	private Searches createSearches(PropertyRepository propertyRepository, SearchRepository searchRepository) {
		SearchFactory searchFactory = new SearchFactory(new PropertyOrderingStrategyFactory());
		SearchAssembler searchAssembler = new SearchAssembler(new SearchCriteriaFactory(), searchFactory);
		SearchEngine searchEngine = new SearchEngine(searchAssembler, propertyRepository);
		Searches searches = new Searches(searchEngine, searchRepository);
		return searches;
	}

}
