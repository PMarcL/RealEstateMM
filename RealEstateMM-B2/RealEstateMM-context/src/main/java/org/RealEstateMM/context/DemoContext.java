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
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.mail.GmailSender;
import org.RealEstateMM.services.mail.MailSender;
import org.RealEstateMM.services.user.mailconfirmation.Base64EmailConfirmationEncoder;
import org.RealEstateMM.services.user.mailconfirmation.EmailConfirmationEncoder;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MailSender mailSender;
	private EmailConfirmationEncoder emailConfirmationEncoder;

	public DemoContext() {
		File xmlUsers = new File(usersFilePath());
		this.userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
		this.propertyRepository = new InMemoryPropertyRepository();
		this.sessionRepository = new InMemorySessionRepository();
		this.mailSender = new GmailSender();
		this.emailConfirmationEncoder = new Base64EmailConfirmationEncoder();
	}

	private String usersFilePath() {
		return XML_FILES_LOCATION + USER_REPOSITORY_FILE;
	}

	@Override
	protected void registerServices() {
		registerRepositories();
		registerMisc();
	}

	private void registerRepositories() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
	}

	private void registerMisc() {
		ServiceLocator.getInstance().registerService(MailSender.class, mailSender);
		ServiceLocator.getInstance().registerService(EmailConfirmationEncoder.class, emailConfirmationEncoder);
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
