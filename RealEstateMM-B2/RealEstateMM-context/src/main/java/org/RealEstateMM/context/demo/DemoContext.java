package org.RealEstateMM.context.demo;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.authentication.session.SessionService;
import org.RealEstateMM.context.Context;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.search.SearchRepository;
import org.RealEstateMM.domain.user.Administrator;
import org.RealEstateMM.domain.user.ExistingUserException;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserNotFoundException;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.locator.ServiceLocator;
import org.RealEstateMM.services.message.MessageService;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.services.search.SearchServiceHandler;
import org.RealEstateMM.services.statistics.StatisticService;
import org.RealEstateMM.services.user.UserServiceHandler;

public class DemoContext extends Context {
	private static final String BASE_URL = "http://localhost:8080/";

	private UserRepository userRepository;
	private SessionRepository sessionRepository;
	private MessageRepository messageRepository;
	private PropertyRepository propertyRepository;
	private SearchRepository searchRepository;
	private SearchServiceHandler searchService;
	private UserServiceHandler userService;
	private PropertyServiceHandler propertyService;
	private StatisticService statsService;
	private SessionService sessionService;
	private MessageService messageService;

	@Override
	protected void registerServices() {
		createRepositories();
		createServices();
		register();
	}

	private void createRepositories() {
		RepositoryFactory factory = new RepositoryFactory();
		userRepository = factory.createUserRepository();
		sessionRepository = factory.createSessionRepository();
		messageRepository = factory.createMessageRepository();
		propertyRepository = factory.createPropertyRepository();
		searchRepository = factory.createSearchRepository();
	}

	private void createServices() {
		searchService = new SearchServiceFactory().create(propertyRepository, searchRepository, userRepository);
		userService = new UserServiceFactory().create(BASE_URL, userRepository);
		propertyService = new PropertyServiceFactory().create(propertyRepository, userRepository);
		statsService = new StatsServiceFactory().create(propertyRepository, userRepository);
		sessionService = new SessionServiceFactory().create(sessionRepository);
		messageService = new MessageServiceFactory().create(messageRepository, userRepository);
	}

	private void register() {
		ServiceLocator.getInstance().registerService(SearchServiceHandler.class, searchService);
		ServiceLocator.getInstance().registerService(UserServiceHandler.class, userService);
		ServiceLocator.getInstance().registerService(PropertyServiceHandler.class, propertyService);
		ServiceLocator.getInstance().registerService(StatisticService.class, statsService);
		ServiceLocator.getInstance().registerService(SessionService.class, sessionService);
		ServiceLocator.getInstance().registerService(MessageService.class, messageService);
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
