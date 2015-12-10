package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.PropertyServiceSecurity;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.property.validation.PropertyServiceAntiCorruption;

public class PropertyServiceFactory {

	public PropertyServiceHandler create(PropertyRepository propertyRepository, UserRepository userRepository) {
		Properties properties = new Properties(propertyRepository);

		PropertyService service = new PropertyService(new PropertyAssembler(), properties);
		PropertyServiceSecurity propertySecurity = new PropertyServiceSecurity(service,
				new UserAuthorizations(userRepository));
		return new PropertyServiceAntiCorruption(propertySecurity, new PropertyInformationsValidator());
	}

}
