package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.persistence.InMemoryPropertyRepository;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.XmlUserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = "c:/data/";
	private static final String USER_REPOSITORY_FILE = "users.xml";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;

	public DemoContext() {
		File xmlUsers = new File(usersFilePath());
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
		this.propertyRepository = new InMemoryPropertyRepository();
		this.sessionRepository = new InMemorySessionRepository();
	}

	private String usersFilePath() {
		return XML_FILES_LOCATION + USER_REPOSITORY_FILE;
	}

	@Override
	protected void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
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