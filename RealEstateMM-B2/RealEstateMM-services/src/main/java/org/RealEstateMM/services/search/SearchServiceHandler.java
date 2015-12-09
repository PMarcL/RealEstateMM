package org.RealEstateMM.services.search;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.dtos.SearchDTO;

public interface SearchServiceHandler {

	public List<PropertyDTO> executeSearch(String pseudo, SearchDTO searchParam) throws ForbiddenAccessException;

	public void saveSearch(String pseudo, SearchDTO search) throws ForbiddenAccessException;

	public List<String> getSavedSearchesForUser(String pseudo) throws ForbiddenAccessException;

	public void deleteSearch(String pseudo, String searchName) throws ForbiddenAccessException;

	public SearchDTO getSearch(String pseudo, String searchName) throws ForbiddenAccessException;

	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException;

	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address)
			throws ForbiddenAccessException, PropertyNotFoundException, InvalidPropertyInformationException;
}
