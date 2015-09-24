package org.RealEstateMM.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import java.util.Optional;
import org.RealEstateMM.domain.account.Account;
import org.RealEstateMM.domain.account.AccountBuilder;
import org.RealEstateMM.domain.account.AccountRepository;
import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.services.dtos.account.AccountAssembler;
import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.dtos.user.UserDTO;
import org.RealEstateMM.services.helpers.AccountDTOBuilder;
import org.RealEstateMM.services.helpers.DefaultUserDTOBuilder;
import org.RealEstateMM.services.roles.RightManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class AccountServiceTest {

	private final User A_USER = new DefaultUserBuilder().build();
	private final UserDTO A_USER_DTO = new DefaultUserDTOBuilder().build();
	private final Account AN_ACCOUNT = new AccountBuilder().withOwner(A_USER).build();
	private final AccountDTO AN_ACCOUNT_DTO = new AccountDTOBuilder().withOwner(A_USER_DTO).build();
	private final String PSEUDO = "pseudo34";

	private UserRepository userRepository;
	private AccountRepository accountRepository;
	private AccountAssembler accountAssembler;

	private AccountService accountService;

	@Before
	public void setUp() throws Exception {
		userRepository = mock(UserRepository.class);
		accountRepository = mock(AccountRepository.class);
		accountAssembler = mock(AccountAssembler.class);

		given(accountAssembler.fromDTO(AN_ACCOUNT_DTO)).willReturn(AN_ACCOUNT);

		accountService = new AccountService(userRepository, accountRepository, accountAssembler);
	}

	@Test
	public void whenCreateAccountAsAnonymousThenAddUserBeforeAccountToRepository() {
		accountService.createAccount(AN_ACCOUNT_DTO);

		InOrder inOrder = inOrder(userRepository, accountRepository);
		inOrder.verify(userRepository, times(1)).addUser(A_USER);
		inOrder.verify(accountRepository, times(1)).addAccount(AN_ACCOUNT);
	}

	@Test
	public void whenAddAnAccountToTheUserLogedInThenAddAccountToRepository() throws NoRightOnThatUserException {
		RightManager rightManager = mock(RightManager.class);
		given(rightManager.isAllowedToEdit(AN_ACCOUNT_DTO.getOwner())).willReturn(true);

		accountService.createAccountWhenLoggedIn(AN_ACCOUNT_DTO, rightManager);

		verify(accountRepository, times(1)).addAccount(AN_ACCOUNT);
	}

	@Test(expected = NoRightOnThatUserException.class)
	public void whenAddAnAccountToAnotherUserThanTheOneLogedInThenThrowNoRightOnThatUserException()
			throws NoRightOnThatUserException {
		RightManager rightManager = mock(RightManager.class);
		given(rightManager.isAllowedToEdit(A_USER_DTO)).willReturn(false);

		accountService.createAccountWhenLoggedIn(AN_ACCOUNT_DTO, rightManager);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceThenCallsTheRepository() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		accountService.userExists(PSEUDO);
		verify(userRepository).getUserWithPseudonym(PSEUDO);
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfNoUserExistsThenReturnsFalse() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.empty());
		assertFalse(accountService.userExists(PSEUDO));
	}

	@Test
	public void givenAPseudonymWhenCheckingForUserExistanceIfUserExistsThenReturnsTrue() {
		when(userRepository.getUserWithPseudonym(PSEUDO)).thenReturn(Optional.of(A_USER));
		assertTrue(accountService.userExists(PSEUDO));
	}
}
