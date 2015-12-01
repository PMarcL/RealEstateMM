package org.RealEstateMM.services.search;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTO;

public interface SearchServiceHandler {

	public List<PropertyDTO> getPropertiesSearchResult(String pseudo, PropertySearchParametersDTO searchParam)
			throws ForbiddenAccessException, InvalidSearchParameterException;

	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException;

	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address) throws ForbiddenAccessException,
			PropertyNotFoundException, InvalidPropertyInformationException;
}
