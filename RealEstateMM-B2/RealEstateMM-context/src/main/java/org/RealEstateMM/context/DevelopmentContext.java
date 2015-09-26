package org.RealEstateMM.context;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformation;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.domain.user.usertype.UserType;
import org.RealEstateMM.domain.user.usertype.UserTypeFactory;
import org.RealEstateMM.persistence.memory.InMemoryUserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class DevelopmentContext extends Context {

	private UserRepository userRepository;
	private UserTypeFactory userTypeFactory;

	public DevelopmentContext() {
		this.userRepository = new InMemoryUserRepository();
		this.userTypeFactory = new UserTypeFactory();
	}

	@Override
	protected void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(UserTypeFactory.class, userTypeFactory);
	}

	@Override
	protected void injectData() {
		UserInformation adminInfo = new UserInformation("admin", "admin1234", "Olivier", "Dugas", "olivierD@admin.com",
				"418 892-3940");
		User admin = new User(adminInfo, UserType.ADMIN);
		userRepository.addUser(admin);
	}

}
