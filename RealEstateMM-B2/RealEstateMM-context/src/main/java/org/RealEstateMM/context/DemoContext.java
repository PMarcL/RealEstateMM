package org.RealEstateMM.context;

import java.io.File;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.emailsender.EmailSender;
import org.RealEstateMM.domain.emailsender.GmailSender;
import org.RealEstateMM.domain.emailsender.email.EmailMessageFactory;
import org.RealEstateMM.domain.encoder.Base64Encoder;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.emailconfirmation.ConfirmationCodeFactory;
import org.RealEstateMM.domain.user.emailconfirmation.UserEmailAddressValidator;
import org.RealEstateMM.persistence.InMemoryPropertyRepository;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.xml.XmlMarshaller;
import org.RealEstateMM.persistence.xml.XmlUserAssembler;
import org.RealEstateMM.persistence.xml.XmlUserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;

public class DemoContext extends Context {
	private static final String XML_FILES_LOCATION = "." + File.separator + "data" + File.separator;
	private static final String USER_REPOSITORY_FILE = "users.xml";
	private static final String BASE_URL = "http://localhost:8080/";

	private UserRepository userRepository;

	public DemoContext() {
		File xmlUsers = new File(usersFilePath());
		userRepository = new XmlUserRepository(new XmlMarshaller(xmlUsers), new XmlUserAssembler());
	}

	private String usersFilePath() {
		return XML_FILES_LOCATION + USER_REPOSITORY_FILE;
	}

	@Override
	protected void registerServices() {
		registerRepositories();
		registerUserEmailValidator();
	}

	private void registerRepositories() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, new InMemoryPropertyRepository());
		ServiceLocator.getInstance().registerService(SessionRepository.class, new InMemorySessionRepository());
	}

	private void registerUserEmailValidator() {
		ConfirmationCodeFactory confirmCodeFactory = new ConfirmationCodeFactory(new Base64Encoder());
		EmailMessageFactory messageFactory = new EmailMessageFactory(BASE_URL);
		EmailSender emailSender = new GmailSender();
		UserEmailAddressValidator validator = new UserEmailAddressValidator(confirmCodeFactory, messageFactory,
				emailSender);

		ServiceLocator.getInstance().registerService(UserEmailAddressValidator.class, validator);
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
