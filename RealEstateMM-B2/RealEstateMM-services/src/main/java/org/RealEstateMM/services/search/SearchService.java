package org.RealEstateMM.services.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.Search;
import org.RealEstateMM.domain.search.SearchEngine;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.search.dtos.SearchAssembler;
import org.RealEstateMM.services.search.dtos.SearchDTO;

public class SearchService implements SearchServiceHandler {

	private SearchEngine searchEngine;
	private PropertyAssembler propertyAssembler;
	private SearchAssembler searchAssembler;

	public SearchService(PropertyAssembler assembler, SearchAssembler searchAssembler) {
		this.searchEngine = ServiceLocator.getInstance().getService(SearchEngine.class);
		this.propertyAssembler = assembler;
		this.searchAssembler = searchAssembler;
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
		List<Property> ownersProperties = searchEngine.getPropertiesFromOwner(owner);
		return buildDTOsFromProperties(ownersProperties);
	}

	@Override
	public List<PropertyDTO> executeSearch(String pseudo, SearchDTO searchDTO)
			throws ForbiddenAccessException, InvalidSearchParameterException {
		Search search = searchAssembler.fromDTO(searchDTO);
		List<Property> searchResults = searchEngine.executeSearch(search);
		return buildDTOsFromProperties(searchResults);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO addressDTO)
			throws ForbiddenAccessException, PropertyNotFoundException {
		PropertyAddress address = propertyAssembler.getAddressFromDTO(addressDTO);
		Property property = searchEngine.getPropertyAtAddress(address);
		return propertyAssembler.toDTO(property);
	}

}