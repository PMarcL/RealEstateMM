package org.RealEstateMM.steps;

import org.RealEstateMM.domain.user.UserAccount;
import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.services.UserConnectionService;
import org.RealEstateMM.services.dto.UserAccountAssembler;
import org.RealEstateMM.services.dto.UserInformations;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class VendorConnectionSteps {

	private final UserAccount A_USER = new UserAccount("bob32", "12345", "Robert", null, null);

	private UserConnectionService connectionService;
	private InMemoryUserRepository repo;
	private UserAccountAssembler assembler;
	private UserInformations returnedUser;

	@Given("an anonymous user")
	public void givenAnAnonymousUser() {
		repo = new InMemoryUserRepository();
		assembler = new UserAccountAssembler();
		connectionService = new UserConnectionService(repo, assembler);

		repo.addUser(A_USER);
	}

	@When("he enters valid vendor credentials")
	public void whenHeEntersValidVendorCredentials() {
		// TODO
	}

	@Then("he is connected to his vendor account")
	public void thenHeIsConnectedToHisVendorAccount() {
		// TODO
	}
}
