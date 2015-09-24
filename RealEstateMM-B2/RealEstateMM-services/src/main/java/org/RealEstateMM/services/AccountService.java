package org.RealEstateMM.services;

import org.RealEstateMM.domain.account.Account;
import org.RealEstateMM.domain.account.AccountRepository;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dtos.account.AccountAssembler;
import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.roles.RightManager;
import org.RealEstateMM.services.servicelocator.ServiceLocator;

public class AccountService {

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private AccountAssembler accountAssembler;

	public AccountService(UserRepository userRepository, AccountRepository accountRepository,
			AccountAssembler accountAssembler) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.accountAssembler = accountAssembler;
	}

	public AccountService() {
		userRepository = ServiceLocator.getInstance().getService(UserRepository.class);
		accountRepository = ServiceLocator.getInstance().getService(AccountRepository.class);
		accountAssembler = new AccountAssembler();

	}

	public void createAccount(AccountDTO accountDTO) {
		Account account = accountAssembler.fromDTO(accountDTO);
		userRepository.addUser(account.getOwner());
		accountRepository.addAccount(account);
	}

	public void createAccountWhenLogedIn(AccountDTO accountDTO, RightManager rightManager)
			throws NoRightOnThatUserException {
		if (!rightManager.isAllowedToEdit(accountDTO.getOwner()))
			throw new NoRightOnThatUserException();
		Account account = accountAssembler.fromDTO(accountDTO);
		accountRepository.addAccount(account);
	}

}
