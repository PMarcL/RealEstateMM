package org.RealEstateMM.services.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.SearchDTO;
import org.RealEstateMM.domain.search.SearchNotFoundException;
import org.RealEstateMM.domain.search.Searches;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public class SearchService implements SearchServiceHandler {

	private Searches searches;
	private PropertyAssembler propertyAssembler;

	public SearchService(PropertyAssembler assembler, Searches searches) {
		this.searches = searches;
		this.propertyAssembler = assembler;
	}

	private List<PropertyDTO> buildDTOsFromProperties(List<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = propertyAssembler.toDTO(property);
			propertiesDTO.add(dto);
		}
		return propertiesDTO;
	}

	@Override
	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException {
		List<Property> ownersProperties = searches.getPropertiesFromOwner(owner);
		return buildDTOsFromProperties(ownersProperties);
	}

	@Override
	public List<PropertyDTO> executeSearch(String pseudo, SearchDTO searchDTO) throws ForbiddenAccessException {
		List<Property> searchResults = searches.executeSearch(searchDTO);
		return buildDTOsFromProperties(searchResults);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO addressDTO)
			throws ForbiddenAccessException, PropertyNotFoundException {
		PropertyAddress address = propertyAssembler.getAddressFromDTO(addressDTO);
		Property property = searches.getPropertyAtAddress(address);
		return propertyAssembler.toDTO(property);
	}

	@Override
	public void saveSearch(String pseudo, SearchDTO searchDTO) throws ForbiddenAccessException {
		searches.save(searchDTO, pseudo);
	}

	@Override
	public List<String> getSavedSearchesForUser(String pseudo) throws ForbiddenAccessException {
		return searches.findSearchesForUser(pseudo);
	}

	@Override
	public void deleteSearch(String pseudo, String searchName) throws ForbiddenAccessException {
		searches.deleteSearch(pseudo, searchName);
	}

	@Override
	public SearchDTO getSearch(String pseudo, String searchName)
			throws ForbiddenAccessException, SearchNotFoundException {
		return searches.getSearch(pseudo, searchName);
	}

}
