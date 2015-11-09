package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.GmailSender;
import org.RealEstateMM.domain.emailsender.email.EmailMessageFactory;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.PropertyInformationsValidator;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.statistics.StatisticService;
import org.RealEstateMM.services.user.UserService;
import org.RealEstateMM.services.user.UserServiceHandler;
import org.RealEstateMM.services.user.anticorruption.UserInformationsValidator;
import org.RealEstateMM.services.user.anticorruption.UserServiceAntiCorruption;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";
	private static final String BASE_URL = "http://localhost:8080/";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;

	private PropertyServiceHandler propertyService;
	private UserServiceHandler userService;
	private StatisticService statisticService;

	public DemoContext() {
		File xmlUsers = new File(usersFilePath());
		File xmlProperty = new File(propertiesFilePath());
		initializeRepositories(xmlUsers, xmlProperty);
		initializeServices();
	}

	private void initializeServices() {
		this.propertyService = new PropertyServiceAntiCorruption(new PropertyService(),
				new PropertyInformationsValidator());
		this.userService = new UserServiceAntiCorruption(new UserService(), new UserInformationsValidator());
		this.statisticService = new StatisticService();
	}

	private void initializeRepositories(File xmlUsers, File xmlProperty) {
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
		this.propertyRepository = new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
		this.sessionRepository = new InMemorySessionRepository();
	}

	private String propertiesFilePath() {
		return XML_FILES_LOCATION + PROPERTY_REPOSITORY_FILE;
	}

	private String usersFilePath() {
		return XML_FILES_LOCATION + USER_REPOSITORY_FILE;
	}

	@Override
	protected void registerServices() {
		registerServiceDependencies();
		ServiceLocator.getInstance().registerService(PropertyServiceHandler.class, propertyService);
		ServiceLocator.getInstance().registerService(UserServiceHandler.class, userService);
		ServiceLocator.getInstance().registerService(StatisticService.class, statisticService);
		// propertyDTOAssembler); //TODO Comment choisir entre les deux
		// constructeurs de PropertyDTOAssembler?
	}

	private void registerServiceDependencies() {
		registerRepositories();
		registerUserEmailValidator();
	}

	private void registerRepositories() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
	}

	private void registerUserEmailValidator() {
		ConfirmationCodeFactory confirmCodeFactory = new ConfirmationCodeFactory(new Base64Encoder());
		EmailMessageFactory messageFactory = new EmailMessageFactory(BASE_URL);
		EmailSender emailSender = new GmailSender();
		UserEmailAddressValidator validator = new UserEmailAddressValidator(confirmCodeFactory, messageFactory,
				emailSender);

		ServiceLocator.getInstance().registerService(UserEmailAddressValidator.class, validator);
	}

	@Override
	protected void injectData() {
		if (isAdminExisting("admin"))
			return;

		UserInformations adminInfo = new UserInformations("admin", "admin1234", "Olivier", "Dugas",
				"olivierD@admin.com", "418 892-3940");
		User admin = new User(adminInfo, new UserType("admin"));
		userRepository.addUser(admin);
	}

	private boolean isAdminExisting(String adminPseudonym) {
		return userRepository.getUserWithPseudonym(adminPseudonym).isPresent();
	}

}
