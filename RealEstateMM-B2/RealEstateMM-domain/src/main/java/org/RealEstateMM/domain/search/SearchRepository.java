package org.RealEstateMM.domain.search;

import java.util.List;

public interface SearchRepository {

	public void persist(SearchDescription searchDescription, String pseudonym);

	public List<SearchDescription> findSearchesForUser(String pseudonym);

	public void remove(String pseudonym, String searchName);

	public SearchDescription getSearchWithNameForUser(String pseudonym, String searchName);

}
