package org.RealEstateMM.services.search;

import java.util.List;

import org.RealEstateMM.domain.property.PropertyNotFoundException;
import org.RealEstateMM.domain.user.ForbiddenAccessException;
import org.RealEstateMM.services.property.InvalidPropertyInformationException;
import org.RealEstateMM.services.property.dtos.PropertyAddressDTO;
import org.RealEstateMM.services.property.dtos.PropertyDTO;

public interface SearchServiceHandler {

	public List<PropertyDTO> getAllProperties(String pseudo) throws ForbiddenAccessException;

	public List<PropertyDTO> getPropertiesFromOwner(String owner) throws ForbiddenAccessException;

	public List<PropertyDTO> getOrderedProperties(String pseudo, String orderBy)
			throws ForbiddenAccessException, InvalidSearchParameterException;

	public PropertyDTO getPropertyAtAddress(String pseudo, PropertyAddressDTO address)
			throws ForbiddenAccessException, PropertyNotFoundException, InvalidPropertyInformationException;
}
