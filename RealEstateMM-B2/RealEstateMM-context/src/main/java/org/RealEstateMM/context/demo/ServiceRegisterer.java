package org.RealEstateMM.context.demo;

import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.PropertyOrderingParametersParser;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.PropertyServiceSecurity;
import org.RealEstateMM.services.property.dtos.PropertyAssembler;
import org.RealEstateMM.services.property.dtos.PropertySearchParametersDTOAssembler;
import org.RealEstateMM.services.property.validation.PropertyInformationsValidator;
import org.RealEstateMM.services.property.validation.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.statistics.StatisticService;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.UserServiceSecurity;
import org.RealEstateMM.services.user.dtos.UserAssembler;
import org.RealEstateMM.services.user.validation.UserInformationsValidator;
import org.RealEstateMM.services.user.validation.UserServiceAntiCorruption;

public class ServiceRegisterer {

	private PropertyServiceAntiCorruption propertyService;
	private UserServiceAntiCorruption userService;
	private StatisticService statisticService;
	private SessionService sessionService;

	public ServiceRegisterer() {
		UserRepository userRepository = ServiceLocator.getInstance().getService(UserRepository.class);

		initializePropertyService(userRepository);
		initializeUserService(userRepository);
		this.statisticService = new StatisticService();
		this.sessionService = new SessionService();
	}

	private void initializeUserService(UserRepository userRepository) {
		UserAssembler userAssembler = new UserAssembler(new UserRoleFactory());
		UserServiceHandler service = new UserService(userAssembler);
		UserServiceSecurity userSecurity = new UserServiceSecurity(service, new UserAuthorizations(userRepository));
		this.userService = new UserServiceAntiCorruption(userSecurity, new UserInformationsValidator());
	}

	private void initializePropertyService(UserRepository userRepository) {
		PropertyAssembler propertyAssembler = new PropertyAssembler();

		PropertyOrderingParametersParser orderingParamParser = new PropertyOrderingParametersParser();
		PropertySearchParametersDTOAssembler searchParamAssembler = new PropertySearchParametersDTOAssembler(
				orderingParamParser);

		PropertyService service = new PropertyService(propertyAssembler, searchParamAssembler);
		PropertyServiceSecurity propertySecurity = new PropertyServiceSecurity(service,
				new UserAuthorizations(userRepository));
		this.propertyService = new PropertyServiceAntiCorruption(propertySecurity, new PropertyInformationsValidator());
	}

	public void register() {
		ServiceLocator.getInstance().registerService(PropertyServiceHandler.class, propertyService);
		ServiceLocator.getInstance().registerService(UserServiceHandler.class, userService);
		ServiceLocator.getInstance().registerService(StatisticService.class, statisticService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);
	}
}
