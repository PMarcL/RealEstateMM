package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.mail.GmailSender;
import org.RealEstateMM.services.mail.MailSender;
import org.RealEstateMM.services.property.PropertyInformationsValidator;
import org.RealEstateMM.services.property.PropertyService;
import org.RealEstateMM.services.property.PropertyServiceAntiCorruption;
import org.RealEstateMM.services.property.PropertyServiceHandler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyAssembler;
import org.RealEstateMM.persistence.xml.property.XmlPropertyRepository;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.user.XmlUserRepository;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String PROPERTY_REPOSITORY_FILE = "properties.xml";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MailSender mailSender;
	private PropertyServiceHandler propertyService;

	public DemoContext() {
		File xmlUsers = new File(usersFilePath());
		File xmlProperty = new File(propertiesFilePath());
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
		this.propertyRepository = new XmlPropertyRepository(new XmlMarshaller(xmlProperty), new XmlPropertyAssembler());
		this.sessionRepository = new InMemorySessionRepository();
		this.mailSender = new GmailSender();
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
		this.propertyService = new PropertyServiceAntiCorruption(new PropertyService(),
				new PropertyInformationsValidator());
		ServiceLocator.getInstance().registerService(PropertyServiceHandler.class, propertyService);
	}

	private void registerServiceDependencies() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
		ServiceLocator.getInstance().registerService(MailSender.class, mailSender);
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
