package org.RealEstateMM.context.demo;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.message.Message;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;
import org.RealEstateMM.services.locator.ServiceLocator;

public class RepositoryRegisterer {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MessageRepository messageRepository;

	public RepositoryRegisterer() {
		initializeUserRepository();
		initializePropertyRepository();
		this.sessionRepository = new InMemorySessionRepository();
		this.messageRepository = new MessageRepository() {
			@Override
			public void add(Message message) {
				// TODO to replace when the implementation is done
			}
		};

	}

	private void initializePropertyRepository() {
		File xmlProperty = new File(XML_FILES_LOCATION + PROPERTY_REPOSITORY_FILE);
		this.propertyRepository = new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
	}

	private void initializeUserRepository() {
		File xmlUsers = new File(XML_FILES_LOCATION + USER_REPOSITORY_FILE);
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler(
				new UserRoleFactory()));
	}

	public void register() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
		ServiceLocator.getInstance().registerService(MessageRepository.class, messageRepository);

	}

}
