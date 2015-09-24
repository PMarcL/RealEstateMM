package org.RealEstateMM.context;

import org.RealEstateMM.domain.account.AccountRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class DevelopmentContext extends Context {

	private UserRepository userRepository;
	private AccountRepository accountRepository;

	public DevelopmentContext() {
		this.userRepository = new InMemoryUserRepository();
		this.accountRepository = new AccountRepository();
	}

	@Override
	protected void registerServices() {
		ServiceLocator.getInstance().registerService(UserRepository.class, userRepository);
		ServiceLocator.getInstance().registerService(AccountRepository.class, accountRepository);

	}

	@Override
	protected void injectData() {
		// No data to inject for now
	}

}
