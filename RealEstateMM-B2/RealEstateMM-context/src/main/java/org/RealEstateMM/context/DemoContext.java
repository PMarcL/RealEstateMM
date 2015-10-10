package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.emailconfirmation.Base64EmailConfirmationEncoder;
import org.RealEstateMM.domain.user.emailconfirmation.EmailConfirmationEncoder;
import org.RealEstateMM.domain.user.emailconfirmation.EmailConfirmer;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.emailsender.EmailSender;
import org.RealEstateMM.emailsender.GmailSender;
import org.RealEstateMM.emailsender.email.EmailFactory;
import org.RealEstateMM.persistence.InMemoryPropertyRepository;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.XmlUserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = ".." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String BASE_URL = "http://localhost:8080";

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;

	private EmailConfirmer emailConfirmer;

	public DemoContext() {
		super(BASE_URL);
		File xmlUsers = new File(usersFilePath());
		initializeRepositories(xmlUsers);
		initializeMisc();
	}

	private void initializeMisc() {
		EmailSender emailSender = new GmailSender();
		EmailConfirmationEncoder emailConfirmationEncoder = new Base64EmailConfirmationEncoder();
		emailConfirmer = new EmailConfirmer(emailSender, emailConfirmationEncoder, new EmailFactory(BASE_URL));
	}

	private void initializeRepositories(File xmlUsers) {
		userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
		propertyRepository = new InMemoryPropertyRepository();
		sessionRepository = new InMemorySessionRepository();
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
		ServiceLocator.getInstance().registerService(EmailConfirmer.class, emailConfirmer);
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
