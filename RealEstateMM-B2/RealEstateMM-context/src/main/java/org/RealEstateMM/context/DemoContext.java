package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.domain.emailsender.EmailMessageFactory;
import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.gmail.GmailSender;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.property.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.property.search.PropertySearchFilterFactory;
import org.RealEstateMM.domain.user.Administrator;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.property.PropertyOrderingParametersParser;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.property.PropertyServiceSecurity;
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

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";
	private static final String BASE_URL = "http://localhost:8080/";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MessageRepository messageRepository;
	private Properties properties;
	private Users users;
	private Messages messages;
	private PropertyServiceHandler propertyService;
	private UserServiceHandler userService;
	private StatisticService statisticService;
	private SessionService sessionService;
	private UserEmailAddressValidator validator;

	private String propertiesFilePath() {
		return XML_FILES_LOCATION + PROPERTY_REPOSITORY_FILE;
	}

	private String usersFilePath() {
		return XML_FILES_LOCATION + USER_REPOSITORY_FILE;
	}

	@Override
	protected void registerServices() {
		initializeServices();
		ServiceLocator.getInstance().registerService(PropertyServiceHandler.class, propertyService);
		ServiceLocator.getInstance().registerService(UserServiceHandler.class, userService);
		ServiceLocator.getInstance().registerService(StatisticService.class, statisticService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);
	}

	private void initializeServices() {
		PropertyServiceSecurity propertySecurity = new PropertyServiceSecurity(new PropertyService(),
				new UserAuthorizations(userRepository));
		this.propertyService = new PropertyServiceAntiCorruption(propertySecurity, new PropertyInformationsValidator());
		UserServiceSecurity userSecurity = new UserServiceSecurity(new UserService(),
				new UserAuthorizations(userRepository));
		this.userService = new UserServiceAntiCorruption(userSecurity, new UserInformationsValidator());
		this.statisticService = new StatisticService();
		this.sessionService = new SessionService();
	}

	@Override
	protected void registerServiceDependencies() {
		ServiceLocator.getInstance().registerService(PropertyOrderingParametersParser.class,
				new PropertyOrderingParametersParser());
		ServiceLocator.getInstance().registerService(UserFilterFactory.class, new UserFilterFactory());
		ServiceLocator.getInstance().registerService(PropertyFilterFactory.class, new PropertyFilterFactory());

		registerUserEmailValidator();
		registerRepositories();
		registerAssemblers();
	}

	private void registerRepositories() {
		initializeRepositories();
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
		ServiceLocator.getInstance().registerService(Properties.class, properties);
		ServiceLocator.getInstance().registerService(Users.class, users);
		ServiceLocator.getInstance().registerService(Messages.class, messages);
	}

	private void initializeRepositories() {
		File xmlUsers = new File(usersFilePath());
		File xmlProperty = new File(propertiesFilePath());
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers),
				new XmlUserAssembler(new UserRoleFactory()));
		this.propertyRepository = new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
		this.sessionRepository = new InMemorySessionRepository();
		this.messageRepository = new MessageRepository() {
			@Override
			public void add(Message message) {
				// TODO Auto-generated method stub
			}
		};
		this.properties = new Properties(propertyRepository, new PropertyOrderingFactory(),
				new PropertySearchFilterFactory());
		this.users = new Users(userRepository, validator);
		this.messages = new Messages(messageRepository, userRepository, messageFactory)

	}

	private void registerUserEmailValidator() {
		ConfirmationCodeFactory confirmCodeFactory = new ConfirmationCodeFactory(new Base64Encoder());
		EmailMessageFactory messageFactory = new EmailMessageFactory(BASE_URL);
		EmailSender emailSender = new GmailSender();
		this.validator = new UserEmailAddressValidator(confirmCodeFactory, messageFactory, emailSender);
		ServiceLocator.getInstance().registerService(UserEmailAddressValidator.class, validator);
	}

	private void registerAssemblers() {
		ServiceLocator.getInstance().registerService(UserAssembler.class, new UserAssembler(new UserRoleFactory()));
		ServiceLocator.getInstance().registerService(PropertySearchParametersDTOAssembler.class,
				new PropertySearchParametersDTOAssembler());
	}

	@Override
	protected void injectData() {
		if (isAdminExisting("ADMIN"))
			return;

		UserInformations adminInfo = new UserInformations("ADMIN", "admin1234", "Olivier", "Dugas",
				"olivierD@admin.com", "418 892-3940");
		User admin = new User(adminInfo, new Administrator());
		admin.unlock();
		try {
			userRepository.addUser(admin);
		} catch (ExistingUserException e) {
			e.printStackTrace();
		}
	}

	private boolean isAdminExisting(String adminPseudonym) {
		try {
			userRepository.getUserWithPseudonym(adminPseudonym);
			return true;
		} catch (UserNotFoundException e) {
			return false;
		}
	}

}
