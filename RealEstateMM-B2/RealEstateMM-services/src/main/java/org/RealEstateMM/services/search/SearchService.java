package org.RealEstateMM.services.search;

import java.util.ArrayList;
import java.util.List;

import org.RealEstateMM.domain.property.Property;
import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.property.informations.PropertyAddress;
import org.RealEstateMM.domain.search.PropertySearchEngine;
import org.RealEstateMM.domain.search.PropertySearchParameters;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTOAssembler;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTOAssembler;

public class SearchService implements SearchServiceHandler {

	private PropertySearchEngine searchEngine;
	private PropertyDTOAssembler assembler;
	private PropertySearchParametersDTOAssembler searchParamAssembler;

	public SearchService() {
		this.searchEngine = ServiceLocator.getInstance().getService(PropertySearchEngine.class);
		this.assembler = new PropertyDTOAssembler();
		searchParamAssembler = ServiceLocator.getInstance().getService(PropertySearchParametersDTOAssembler.class);
	}

	public SearchService(PropertySearchEngine searchEngine, PropertyDTOAssembler assembler,
			PropertySearchParametersDTOAssembler searchParamAssembler) {
		this.searchEngine = searchEngine;
		this.assembler = assembler;
		this.searchParamAssembler = searchParamAssembler;
	}

	private List<PropertyDTO> buildDTOsFromProperties(List<Property> properties) {
		ArrayList<PropertyDTO> propertiesDTO = new ArrayList<PropertyDTO>();
		for (Property property : properties) {
			PropertyDTO dto = assembler.toDTO(property);
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
	public List<PropertyDTO> getPropertiesSearchResult(String pseudo, PropertySearchParametersDTO searchParamDTO)
			throws ForbiddenAccessException, InvalidSearchParameterException {
		PropertySearchParameters searchParams = searchParamAssembler.fromDTO(searchParamDTO);
		List<Property> allProperties = searchEngine.getPropertiesSearchResults(searchParams);
		return buildDTOsFromProperties(allProperties);
	}

	@Override
	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO addressDTO)
			throws ForbiddenAccessException, PropertyNotFoundException {
		PropertyAddress address = assembler.getAddressFromDTO(addressDTO);
		Property property = searchEngine.getPropertyAtAddress(address);
		return assembler.toDTO(property);
	}

}
