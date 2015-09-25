package org.RealEstateMM.context;

import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.persistence.InMemoryUserRepository;
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
		// No data to inject for now
	}

}
