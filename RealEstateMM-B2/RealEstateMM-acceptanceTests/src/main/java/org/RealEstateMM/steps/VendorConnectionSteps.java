package org.RealEstateMM.steps;

import org.RealEstateMM.domain.models.user.User;
import org.RealEstateMM.persistence.InMemoryUserRepository;
import org.RealEstateMM.testdata.DefaultUserBuilder;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class VendorConnectionSteps {

	private final User A_USER = new DefaultUserBuilder().build();

	private InMemoryUserRepository repo;

	@Given("an anonymous user")
	public void givenAnAnonymousUser() {
		repo = new InMemoryUserRepository();

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
