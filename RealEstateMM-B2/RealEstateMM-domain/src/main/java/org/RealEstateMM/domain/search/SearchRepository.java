package org.RealEstateMM.domain.search;

public interface SearchRepository {

	public void persist(SearchDescription searchDescription, String pseudonym);

}
