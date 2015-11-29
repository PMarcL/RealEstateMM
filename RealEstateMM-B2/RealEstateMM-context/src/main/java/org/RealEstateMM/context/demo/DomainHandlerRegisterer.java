package org.RealEstateMM.context.demo;

import org.RealEstateMM.domain.emailsender.EmailMessageFactory;
import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.gmail.GmailSender;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.message.Messages;
import org.RealEstateMM.domain.property.Properties;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.Users;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class DomainHandlerRegisterer {
	private String baseUrl;

	private Properties properties;
	private Users users;
	private Messages messages;

	public DomainHandlerRegisterer(String baseUrl) {
		this.baseUrl = baseUrl;

		UserRepository userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		PropertyRepository propertyRepository = ServiceLocator.getInstance().getService(PropertyRepository.class);
		MessageRepository messageRepository = ServiceLocator.getInstance().getService(MessageRepository.class);

		this.properties = new Properties(propertyRepository);
		this.messages = new Messages(messageRepository, userRepository);
		initializeUsers(userRepository);
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
	}
}
