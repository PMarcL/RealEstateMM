package org.RealEstateMM.domain.search;

import java.util.List;

public interface SearchRepository {

	public void addSearch(SearchDTO search, String pseudonym);

	public List<SearchDescription> getSearchesForUser(String pseudonym);

	public void remove(String pseudonym, String searchName);

	public SearchDTO getSearchWithNameForUser(String pseudonym, String searchName) throws SearchNotFoundException;

}
