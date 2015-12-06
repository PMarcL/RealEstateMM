package org.RealEstateMM.context.demo;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.message.XmlMessageAssembler;
import org.RealEstateMM.persistence.xml.message.XmlMessageRepository;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;
import org.RealEstateMM.services.locator.ServiceLocator;

public class RepositoryRegisterer {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";
	private static final String MESSAGE_REPOSITORY_FILE = "messages.xml";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MessageRepository messageRepository;

	public RepositoryRegisterer() {
		initializeUserRepository();
		initializePropertyRepository();
		initializeMessageRepository();
		this.sessionRepository = new InMemorySessionRepository();

	}

	private void initializePropertyRepository() {
		File xmlProperty = new File(XML_FILES_LOCATION + PROPERTY_REPOSITORY_FILE);
		this.propertyRepository = new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
	}

	private void initializeUserRepository() {
		File xmlUsers = new File(XML_FILES_LOCATION + USER_REPOSITORY_FILE);
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers),
				new XmlUserAssembler(new UserRoleFactory()));
	}
	
	private void initializeMessageRepository(){
		File xmlMessage = new File(XML_FILES_LOCATION + MESSAGE_REPOSITORY_FILE);
		this.messageRepository = new XmlMessageRepository(new XmlMarshaller(xmlMessage), new XmlMessageAssembler());
	}

	public void register() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
		ServiceLocator.getInstance().registerService(MessageRepository.class, messageRepository);

	}

}
