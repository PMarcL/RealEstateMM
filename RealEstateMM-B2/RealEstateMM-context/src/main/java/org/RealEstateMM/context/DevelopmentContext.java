package org.RealEstateMM.context;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserType;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.persistence.memory.InMemoryUserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class DevelopmentContext extends Context {

	private UserRepository userRepository;

	public DevelopmentContext() {
		this.userRepository = new InMemoryUserRepository();
	}

	@Override
	protected void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
	}

	@Override
	protected void injectData() {
		UserInformations adminInfo = new UserInformations("admin", "admin1234", "Olivier", "Dugas",
				"olivierD@admin.com", "418 892-3940");
		User admin = new User(adminInfo, new UserType("admin"));
		userRepository.addUser(admin);
	}

}
