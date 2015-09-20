package org.RealEstateMM.steps;

import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserRepository;
import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.services.UserConnectionService;
import org.RealEstateMM.services.dto.UserInformations;
import org.RealEstateMM.services.dto.UserInformationsAssembler;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class VendorConnectionSteps {
	private final User A_USER = new User("bob32", "12345", "Robert", "Fross", "email@hotmail.com", "819 938-3950");

	private UserConnectionService connectionService;
	private UserRepository userRepository;
	private UserInformationsAssembler assembler;
	private UserInformations returnedUser;

	@Given("an anonymous user")
	public void givenAnAnonymousUser() {
		userRepository = new InMemoryUserRepository();
		assembler = new UserInformationsAssembler();
		connectionService = new UserConnectionService(userRepository, assembler);

		userRepository.addUser(A_USER);
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
