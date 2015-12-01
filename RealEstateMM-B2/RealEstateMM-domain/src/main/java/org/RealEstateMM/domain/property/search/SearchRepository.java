package org.RealEstateMM.domain.property.search;

import java.util.ArrayList;
import java.util.Optional;

public interface SearchRepository {

	void add(Search newSearch);

	void updateSearch(Search search);

	ArrayList<Search> getSearchesOfUser(String userPseudonyme);

	ArrayList<Search> getAll();

	Optional<Search> getSearchWithName(String searchName);

}
