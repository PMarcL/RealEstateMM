package org.RealEstateMM.context.demo;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.message.MessageRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.search.SearchRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.message.XmlMessageAssembler;
import org.RealEstateMM.persistence.xml.message.XmlMessageRepository;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.search.XmlSearchAssembler;
import org.RealEstateMM.persistence.xml.search.XmlSearchRespository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;

public class RepositoryFactory {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";
	private static final String MESSAGE_REPOSITORY_FILE = "messages.xml";
	private static final String SEARCH_REPOSITORY_FILE = "searches.xml";

	public PropertyRepository createPropertyRepository() {
		File xmlProperty = new File(XML_FILES_LOCATION + PROPERTY_REPOSITORY_FILE);
		return new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
	}

	public UserRepository createUserRepository() {
		File xmlUsers = new File(XML_FILES_LOCATION + USER_REPOSITORY_FILE);
		return new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler(new UserRoleFactory()));
	}

	public MessageRepository createMessageRepository() {
		File xmlMessage = new File(XML_FILES_LOCATION + MESSAGE_REPOSITORY_FILE);
		return new XmlMessageRepository(new XmlMarshaller(xmlMessage), new XmlMessageAssembler());
	}

	public SessionRepository createSessionRepository() {
		return new InMemorySessionRepository();
	}

	public SearchRepository createSearchRepository() {
		File xmlSearch = new File(XML_FILES_LOCATION + SEARCH_REPOSITORY_FILE);
		return new XmlSearchRespository(new XmlMarshaller(xmlSearch), new XmlSearchAssembler());
	}

}
