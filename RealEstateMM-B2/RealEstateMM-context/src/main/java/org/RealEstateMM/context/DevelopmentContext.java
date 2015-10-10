package org.RealEstateMM.context;

import org.RealEstateMM.authentication.session.SessionRepository;
import org.RealEstateMM.domain.property.PropertyRepository;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.persistence.InMemoryPropertyRepository;
import org.RealEstateMM.persistence.memory.InMemorySessionRepository;
import org.RealEstateMM.persistence.memory.InMemoryUserRepository;
import org.RealEstateMM.servicelocator.ServiceLocator;
import org.RealEstateMM.services.mail.GmailSender;
import org.RealEstateMM.services.mail.MailSender;

public class DevelopmentContext extends Context {

	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	private SessionRepository sessionRepository;
	private MailSender mailSender;

	public DevelopmentContext() {
		this.userRepository = new InMemoryUserRepository();
		this.propertyRepository = new InMemoryPropertyRepository();
		this.sessionRepository = new InMemorySessionRepository();
		this.mailSender = new GmailSender();
	}

	@Override
	protected void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(PropertyRepository.class, propertyRepository);
		ServiceLocator.getInstance().registerService(SessionRepository.class, sessionRepository);
		ServiceLocator.getInstance().registerService(MailSender.class, mailSender);
	}

	@Override
	protected void injectData() {
		UserInformations adminInfo = new UserInformations("admin", "admin1234", "Olivier", "Dugas",
				"olivierD@admin.com", "418 892-3940");
		User admin = new User(adminInfo, new UserType("admin"));
		userRepository.addUser(admin);
	}

}
