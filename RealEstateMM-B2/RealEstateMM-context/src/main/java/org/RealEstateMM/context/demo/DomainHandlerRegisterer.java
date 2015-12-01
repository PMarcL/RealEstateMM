package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.gmail.GmailSender;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.message.MessageFactory;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.property.filters.PropertyFilterFactory;
import org.RealEstateMM.domain.search.PropertyOrderingFactory;
import org.RealEstateMM.domain.search.PropertySearchEngine;
import org.RealEstateMM.domain.search.PropertySearchFilterFactory;
import org.RealEstateMM.domain.statistics.Statistics;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.EmailMessageFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.domain.user.filters.UserFilterFactory;
import org.RealEstateMM.services.locator.ServiceLocator;

public class DomainHandlerRegisterer {
	private String baseUrl;

	private Properties properties;
	private Users users;
	private PropertySearchEngine searchEngine;
	private Messages messages;
	private Statistics statistics;

	public DomainHandlerRegisterer(String baseUrl) {
		this.baseUrl = baseUrl;

		UserRepository userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		PropertyRepository propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		MessageRepository messageRepository = ServiceLocator.getInstance().getService(MessageRepository.class);

		initializeUsers(userRepository);
		initializeProperties(propertyRepository);
		initializeSearchEngine(propertyRepository);
		this.messages = new Messages(messageRepository, userRepository, new MessageFactory());
		this.statistics = new Statistics(propertyRepository, userRepository, new UserFilterFactory(),
				new PropertyFilterFactory());
	}

	private void initializeProperties(PropertyRepository propertyRepository) {
		this.properties = new Properties(propertyRepository);
	}

	private void initializeSearchEngine(PropertyRepository propertyRepository) {
		PropertyOrderingFactory orderingFactory = new PropertyOrderingFactory();
		PropertySearchFilterFactory filterFactory = new PropertySearchFilterFactory(new PropertyFilterFactory());
		this.searchEngine = new PropertySearchEngine(propertyRepository, orderingFactory, filterFactory);
	}

	private void initializeUsers(UserRepository userRepository) {
		ConfirmationCodeFactory confirmCodeFactory = new ConfirmationCodeFactory(new Base64Encoder());
		EmailMessageFactory emailMessageFactory = new EmailMessageFactory(baseUrl);
		EmailSender emailSender = new GmailSender();

		UserEmailAddressValidator validator = new UserEmailAddressValidator(confirmCodeFactory, emailMessageFactory,
				emailSender);

		this.users = new Users(userRepository, validator);
	}

	public void register() {
		ServiceLocator.getInstance().registerService(Properties.class, properties);
		ServiceLocator.getInstance().registerService(Users.class, users);
		ServiceLocator.getInstance().registerService(Messages.class, messages);
		ServiceLocator.getInstance().registerService(Statistics.class, statistics);
		ServiceLocator.getInstance().registerService(PropertySearchEngine.class, searchEngine);
	}
}
