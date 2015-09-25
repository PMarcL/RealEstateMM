package org.RealEstateMM.steps;

import org.RealEstateMM.domain.helpers.DefaultUserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.repository.UserRepository;
import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class VendorConnectionSteps {
	private final User A_USER = new DefaultUserBuilder().build();

	private UserRepository userRepository;

	@Given("an anonymous user")
	public void givenAnAnonymousUser() {
		userRepository = new InMemoryUserRepository();

		userRepository.addUser(A_USER);
	}

	@When("he enters valid vendor credentials")
	public void whenHeEntersValidVendorCredentials() {
		// TODO acceptation test
	}

	@Then("he is connected to his vendor account")
	public void thenHeIsConnectedToHisVendorAccount() {
		// TODO acceptation test
	}
}
